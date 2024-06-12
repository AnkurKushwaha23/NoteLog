package com.example.noatlog.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noatlog.Adapter.NotesAdapter
import com.example.noatlog.Adapter.OnNoteLongClickListener
import com.example.noatlog.MainActivity
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.R
import com.example.noatlog.ViewModel.NotesViewModel
import com.example.noatlog.databinding.FragmentHomeBinding

class HomeFragment : Fragment() , OnNoteLongClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var viewModel: NotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchNotes(newText)
                }
                return true
            }
        })
    }


    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter(findNavController(),this)
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = notesAdapter
        }

        viewModel.allNotes().observe(viewLifecycleOwner) { notes ->
            notesAdapter.differ.submitList(notes)
            updateUI(notes)
        }
    }

    private fun searchNotes(query: String) {
        viewModel.searchNotes(query).observe(viewLifecycleOwner) { notes ->
            notesAdapter.differ.submitList(notes)
            updateUI(notes)
        }
    }

    private fun updateUI(notes: List<NotesModel>?) {
        if (notes != null && notes.isNotEmpty()) {
            binding.imgEmpty.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.imgEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }
    override fun onNoteLongClicked(note: NotesModel) {
        // Show a confirmation dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Delete the note from the database
                viewModel.delete(note)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}