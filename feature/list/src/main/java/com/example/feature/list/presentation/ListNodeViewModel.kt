package com.example.feature.list.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.ui.BaseViewModel
import com.example.core.ui.view.UiState
import com.example.core.util.flow.mutableStateIn
import com.example.data.model.Note
import com.example.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNodeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Note>>> = noteRepository.getNoteStream().map {
        UiState.Success(it)
    }.mutableStateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading
    )
    val uiState: StateFlow<UiState<List<Note>>>
        get() = _uiState

    private val _removeNodeState: MutableSharedFlow<UiState<Unit>> = MutableSharedFlow()
    val createNodeState: SharedFlow<UiState<Unit>>
        get() = _removeNodeState

    fun removeNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
                .onStart { UiState.Loading }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    UiState.Error("")
                }.collect {
                    _removeNodeState.emit(UiState.Success(Unit))
                }
        }
    }
}


