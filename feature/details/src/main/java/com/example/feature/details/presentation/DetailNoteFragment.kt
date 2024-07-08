package com.example.feature.details.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.ui.fragment.BaseFragment
import com.example.core.ui.view.UiState
import com.example.core.util.view.safeOnClickListener
import com.example.data.model.Note
import com.example.feature.details.databinding.FragmentDetailNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailNoteFragment :
    BaseFragment<FragmentDetailNoteBinding>(FragmentDetailNoteBinding::inflate) {
    private val args: DetailNoteFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailNodeViewModel>()
    override fun initViews() {
        //
    }

    override fun observeViewModels() {
        args.noteId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> showLoading(true)
                        is UiState.Success -> {
                            initNote(it.data)
                            showLoading(false)
                        }

                        else -> {
                            showLoading(false)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createNodeState.collect {
                    when (it) {
                        is UiState.Loading -> showLoading(true)
                        is UiState.Success -> {
                            findNavController().popBackStack()
                        }
                        else -> {
                            showLoading(false)
                        }
                    }
                }
            }
        }

        binding.fabAddNote.safeOnClickListener {
            viewModel.note.title = binding.edTitle.text.toString()
            viewModel.note.description = binding.edDescription.text.toString()
            viewModel.createOrEditNote(viewModel.note)
        }
    }

    private fun initNote(note: Note) {
        with(binding) {
            edTitle.setText(note.title)
            edDescription.setText(note.description)
            viewModel.note = note
        }
    }
}