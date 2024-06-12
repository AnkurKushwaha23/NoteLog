package com.example.noatlog.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noatlog.Model.NotesModel

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesModel)

    @Query("UPDATE notes_table SET title = :title, noteDesc = :noteDesc, date = :date WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, noteDesc: String, date: String)

    @Delete
    suspend fun deleteNote(note: NotesModel)

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNote(): LiveData<List<NotesModel>>

    @Query("SELECT * FROM notes_table WHERE title LIKE '%' || :query || '%' OR noteDesc LIKE '%' || :query || '%'")
    fun searchNote(query: String): LiveData<List<NotesModel>>
}
