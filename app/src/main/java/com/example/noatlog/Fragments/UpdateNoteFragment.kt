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
import androidx.navigation.fragment.navArgs
import com.example.noatlog.MainActivity
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.R
import com.example.noatlog.ViewModel.NotesViewModel
import com.example.noatlog.databinding.FragmentUpdateNoteBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UpdateNoteFragment : Fragment() {

    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var viewModel: NotesViewModel

    private lateinit var currentNotesModel: NotesModel
    private val args: UpdateNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        currentNotesModel = args.notes!!
        binding.etUpdateTitle.setText(currentNotesModel.title)
        binding.etUpdateNotes.setText(currentNotesModel.noteDesc)

        binding.btnUpdate.setOnClickListener {
            updateNote()
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateNote() {

        val title = binding.etUpdateTitle.text.toString().trim()
        val note = binding.etUpdateNotes.text.toString().trim()
        val id = currentNotesModel.id.toInt()

        if (title.isNotEmpty()) {
            val newNote = NotesModel(
                id = id,
                title = title,
                noteDesc = note,
                date = getCurrentDate()
            )
            viewModel.update(newNote)
            Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(
                requireContext(),
                "Title and Note cannot be empty",
                Toast.LENGTH_SHORT
            )
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