package com.example.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}