package com.example.noatlog.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noatlog.Model.NotesModel

@Database(entities = [NotesModel::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase? = null
        fun getDatabase(context: Context): NotesDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notesDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}