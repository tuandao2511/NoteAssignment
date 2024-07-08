package com.example.data.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    /**
     * Observes list of notes.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM note")
    fun observeAll(): Flow<List<NoteEntity>>

    /**
     * Observes a single note.
     *
     * @param noteId the note id.
     * @return the note with noteId.
     */
    @Query("SELECT * FROM note WHERE id = :noteId")
    fun observeById(noteId: String): Flow<NoteEntity?>

    /**
     * Insert or update a note in the database. If a task already exists, replace it.
     *
     * @param note the task to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(note: NoteEntity)

    /**
     * Delete a note by id.
     *
     * @return the number of notes deleted. This should always be 1.
     */
    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteById(noteId: String): Int

}