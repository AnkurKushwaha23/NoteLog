package com.example.noatlog.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController

import com.example.noatlog.MainActivity
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.R
import com.example.noatlog.ViewModel.NotesViewModel
import com.example.noatlog.databinding.FragmentNewNoteBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NewNoteFragment : Fragment() {

    private lateinit var binding: FragmentNewNoteBinding
    private lateinit var viewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewNoteBinding.inflate(inflater, container, false)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.btnSave.setOnClickListener {
            saveNote()
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {
        val title = binding.etNewTitle.text.toString().trim()
        val note = binding.etNewNotes.text.toString().trim()

        if (title.isNotEmpty()) {
            val newNote = NotesModel(
                id = 0,
                title = title,
                noteDesc = note,
                date = getCurrentDate()
            )
            viewModel.insert(newNote)
            Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return currentDate.format(formatter)
    }

}