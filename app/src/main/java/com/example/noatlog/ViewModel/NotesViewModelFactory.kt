package com.example.noatlog.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noatlog.Repository.NotesRepository

class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(repository) as T
    }
}