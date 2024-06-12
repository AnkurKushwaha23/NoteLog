package com.example.noatlog.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.Repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel(){
    fun allNotes() = repository.allNotes()

    fun searchNotes(query : String) = repository.searchNotes(query)

    fun insert(notesModel: NotesModel) = viewModelScope.launch {
        repository.insert(notesModel)
    }

    fun delete(notesModel: NotesModel) = viewModelScope.launch {
        repository.delete(notesModel)
    }

    fun update(notesModel: NotesModel) = viewModelScope.launch {
        repository.update(notesModel)
    }

}