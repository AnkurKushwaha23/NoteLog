package com.example.noatlog


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.noatlog.Repository.NotesRepository
import com.example.noatlog.RoomDB.NotesDatabase
import com.example.noatlog.ViewModel.NotesViewModel
import com.example.noatlog.ViewModel.NotesViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()


    }

    private fun setupViewModel() {
        val notesDao = NotesDatabase.getDatabase(applicationContext).notesDao()
        val notesRepository = NotesRepository(notesDao)
        val viewModelProviderFactory = NotesViewModelFactory(notesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NotesViewModel::class.java]
    }

}
