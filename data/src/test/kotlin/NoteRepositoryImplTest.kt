import com.example.data.datasource.NoteDao
import com.example.data.entity.NoteEntity
import com.example.data.model.Note
import com.example.data.model.toNote
import com.example.data.model.toNoteEntity
import com.example.data.repository.NoteRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteRepositoryImplTest {

    private lateinit var noteDao: NoteDao
    private lateinit var noteRepository: NoteRepositoryImpl

    @Before
    fun setUp() {
        noteDao = mockk()
        noteRepository = NoteRepositoryImpl(noteDao)
    }

    @Test
    fun `getNoteStream returns flow of list of notes`() = runTest {
        // Given
        val noteList = listOf(NoteEntity("1", "Note1", "Content1"), NoteEntity("2", "Note2", "Content2"))
        every { noteDao.observeAll() } returns flowOf(noteList)

        // When
        val result = noteRepository.getNoteStream().first()

        // Then
        assertEquals(noteList.map { it.toNote() }, result)
        verify { noteDao.observeAll() }
    }

    @Test
    fun `getNoteStream with noteId returns flow of note`() = runTest {
        // Given
        val noteId = "1"
        val noteEntity = NoteEntity(noteId, "Note1", "Content1")
        every { noteDao.observeById(noteId) } returns flowOf(noteEntity)

        // When
        val result = noteRepository.getNoteStream(noteId).first()

        // Then
        assertEquals(noteEntity.toNote(), result)
        verify { noteDao.observeById(noteId) }
    }

    @Test
    fun `createNote inserts note and emits Unit`() = runTest {
        // Given
        val note = Note("1", "Note1", "Content1")
        coEvery { noteDao.upsert(note.toNoteEntity()) } returns Unit

        // When
        val result = noteRepository.createNote(note).first()

        // Then
        assertEquals(Unit, result)
        coVerify { noteDao.upsert(note.toNoteEntity()) }
    }

    @Test
    fun `deleteNote deletes note by id and emits Unit`() = runTest {
        // Given
        val noteId = "1"
        coEvery { noteDao.deleteById(noteId) } returns 1

        // When
        val result = noteRepository.deleteNote(noteId).first()

        // Then
        assertEquals(Unit, result)
        coVerify { noteDao.deleteById(noteId) }
    }
}