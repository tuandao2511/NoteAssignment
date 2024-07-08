package com.example.feature.list.persentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.ui.view.UiState
import com.example.data.model.Note
import com.example.data.repository.NoteRepository
import com.example.feature.list.presentation.ListNodeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListNodeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteRepository: NoteRepository
    private lateinit var viewModel: ListNodeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        noteRepository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits success when getNoteStream emits notes`() = runTest {
        // Given
        val notes = listOf(Note("1", "Note1", "Content1"), Note("2", "Note2", "Content2"))
        val flowNotes = flowOf(notes)
        every { noteRepository.getNoteStream() } returns flowNotes

        viewModel = ListNodeViewModel(noteRepository)

        advanceUntilIdle()

        val uiState = viewModel.uiState.first { it is UiState.Success }
        assertEquals(UiState.Success(notes), uiState)
        verify { noteRepository.getNoteStream() }
    }

    @Test
    fun `removeNote emits loading and success states`() = runTest {
        val noteId = "1"
        coEvery { noteRepository.deleteNote(noteId) } returns flowOf(Unit)
        val notes = listOf(Note("1", "Note1", "Content1"), Note("2", "Note2", "Content2"))
        val flowNotes = flowOf(notes)
        every { noteRepository.getNoteStream() } returns flowNotes
        viewModel = ListNodeViewModel(noteRepository)

        viewModel.removeNote(noteId)
        advanceUntilIdle()

        val createNodeState = viewModel.createNodeState.first()
        assertEquals(UiState.Success(Unit), createNodeState)
        coVerify { noteRepository.deleteNote(noteId) }
    }

}