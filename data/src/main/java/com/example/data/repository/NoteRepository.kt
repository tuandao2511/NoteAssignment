package com.example.data.repository

import com.example.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteStream(): Flow<List<Note>>

    fun getNoteStream(noteId: String?): Flow<Note?>

    suspend fun createNote(note: Note) : Flow<Unit>


    suspend fun deleteNote(noteId: String) : Flow<Unit>
}