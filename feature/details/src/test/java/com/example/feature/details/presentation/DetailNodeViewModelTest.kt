package com.example.feature.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.core.ui.view.UiState
import com.example.core.util.navigate.RouteAppUntil
import com.example.data.model.Note
import com.example.data.repository.NoteRepository
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
class DetailNodeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteRepository: NoteRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailNodeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        noteRepository = mockk()
        savedStateHandle = SavedStateHandle(mapOf(RouteAppUntil.KEY_NODE_ID to "test_note_id"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits success when getNoteStream emits note`() = runTest {
        val note = Note("1", "Note1", "Content1")
        val flowNote = flowOf(note)
        every { noteRepository.getNoteStream("test_note_id") } returns flowNote

        viewModel = DetailNodeViewModel(savedStateHandle, noteRepository)

        advanceUntilIdle() // Ensure all coroutines have completed

        val uiState = viewModel.uiState.first { it is UiState.Success }
        assertEquals(UiState.Success(note), uiState)
        verify { noteRepository.getNoteStream("test_note_id") }
    }

    @Test
    fun `createOrEditNote emits loading and success states`() = runTest {

        // Given
        val note = Note("1", "Note1", "Content1")
        coEvery { noteRepository.createNote(note) } returns flowOf(Unit)

        val flowNote = flowOf(note)
        every { noteRepository.getNoteStream("test_note_id") } returns flowNote

        viewModel = DetailNodeViewModel(savedStateHandle, noteRepository)

        viewModel.createOrEditNote(note)
        advanceUntilIdle() // Ensure all coroutines have completed

        val createNodeState = viewModel.createNodeState.first { it is UiState.Success }
        assertEquals(UiState.Success(Unit), createNodeState)
        coVerify { noteRepository.createNote(note) }
    }
}