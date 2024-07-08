package com.example.feature.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.core.ui.view.UiState
import com.example.core.util.flow.mutableStateIn
import com.example.core.util.navigate.RouteAppUntil
import com.example.data.model.Note
import com.example.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailNodeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : BaseViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _uiState: MutableStateFlow<UiState<Note>> =
        savedStateHandle.getStateFlow(RouteAppUntil.KEY_NODE_ID, "").flatMapConcat { noteId ->
            noteRepository.getNoteStream(noteId).map {
                UiState.Success(it ?: Note("", "", UUID.randomUUID().toString()))
            }
        }.mutableStateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading
        )
    val uiState: StateFlow<UiState<Note>> = _uiState
    private val _createNodeState: MutableSharedFlow<UiState<Unit>> = MutableSharedFlow()
    val createNodeState: SharedFlow<UiState<Unit>>
        get() = _createNodeState
    var note: Note = Note("", "", UUID.randomUUID().toString())

    fun createOrEditNote(note: Note) {
        viewModelScope.launch {
            noteRepository.createNote(note)
                .onStart { UiState.Loading }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    UiState.Error("")
                }.collect {
                    _createNodeState.emit(UiState.Success(Unit))
                }
        }
    }
}