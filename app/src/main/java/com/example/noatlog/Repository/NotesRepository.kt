package com.example.noatlog.Repository

import androidx.lifecycle.LiveData
import com.example.noatlog.Model.NotesModel
import com.example.noatlog.RoomDB.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    fun allNotes(): LiveData<List<NotesModel>> = notesDao.getAllNote()

    fun searchNotes(query: String): LiveData<List<NotesModel>> = notesDao.searchNote(query)

    suspend fun insert(notesModel: NotesModel) = notesDao.insertNote(notesModel)

    suspend fun delete(notesModel: NotesModel) = notesDao.deleteNote(notesModel)

    suspend fun update(notesModel: NotesModel) =
        notesDao.updateNote(notesModel.id, notesModel.title, notesModel.noteDesc, notesModel.date)

}