package com.example.data.repository

import com.example.data.datasource.NoteDao
import com.example.data.model.Note
import com.example.data.model.toListNote
import com.example.data.model.toNote
import com.example.data.model.toNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: NoteDao
) : NoteRepository {
    override fun getNoteStream(): Flow<List<Note>> {
        return localDataSource.observeAll().map { notes -> notes.toListNote() }
    }

    override fun getNoteStream(noteId: String?): Flow<Note?> {
        if (noteId.isNullOrEmpty()) return flowOf(Note("", "", UUID.randomUUID().toString()))
        return localDataSource.observeById(noteId).map { it?.toNote() }
    }

    override suspend fun createNote(note: Note) = flow {
        localDataSource.upsert(note.toNoteEntity())
        emit(Unit)
    }

    override suspend fun deleteNote(noteId: String) = flow {
        localDataSource.deleteById(noteId)
        emit(Unit)
    }
}