package com.example.noatlog.Model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class NotesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val noteDesc: String,
    val date: String
) : Parcelable
