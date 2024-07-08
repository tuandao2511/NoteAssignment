package com.example.feature.list.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ui.fragment.BaseFragment
import com.example.core.ui.view.UiState
import com.example.core.util.eventbus.MainEvent
import com.example.core.util.eventbus.MainEventDispatcher
import com.example.feature.list.adapter.NoteListAdapter
import com.example.feature.list.databinding.FragmentListNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListNodeFragment : BaseFragment<FragmentListNoteBinding>(FragmentListNoteBinding::inflate) {
    private val viewModel by viewModels<ListNodeViewModel>()
    lateinit var adapter: NoteListAdapter

    override fun initViews() {
        initList()
    }

    override fun observeViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when(it) {
                        is UiState.Loading -> showLoading(true)
                        is UiState.Success-> {
                            showLoading(false)
                            adapter.setListItem(it.data)
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
                        }
                        else -> {
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun initList() {
        with(binding) {
            adapter = NoteListAdapter(onClickListener = {
                viewLifecycleOwner.lifecycleScope.launch {
                    MainEventDispatcher.dispatchEvent(MainEvent.Detail(it))
                }
            }, onRemoveListener = {
                viewModel.removeNote(it)
            })
            rvListNote.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvListNote.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            rvListNote.adapter = adapter

            fabAddNote.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    MainEventDispatcher.dispatchEvent(MainEvent.Detail())
                }
            }
        }
    }
}