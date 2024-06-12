package com.example.noatlog.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noatlog.Fragments.HomeFragmentDirections
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.databinding.ListItemBinding


class NotesAdapter(
    private val navController: NavController,
    private val onNoteLongClickListener: OnNoteLongClickListener // Add this parameter
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.bind(note)
    }

    override fun getItemCount() = differ.currentList.size

    inner class NotesViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NotesModel) {
            binding.apply {
                tvTitle.text = note.title
                tvNote.text = note.noteDesc
                tvDate.text = note.date
                cardView.setOnClickListener {
                    val direction =
                        HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note)
                    navController.navigate(direction)
                }
                cardView.setOnLongClickListener {
                    onNoteLongClickListener.onNoteLongClicked(note)
                    true
                }
            }
        }
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<NotesModel>() {
        override fun areItemsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)
}

interface OnNoteLongClickListener {
    fun onNoteLongClicked(note: NotesModel)
}