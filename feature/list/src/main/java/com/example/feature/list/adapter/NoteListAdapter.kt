package com.example.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.ui.BaseAdapter
import com.example.core.ui.BaseViewHolder
import com.example.core.util.view.safeOnClickListener
import com.example.data.model.Note
import com.example.feature.list.R
import com.example.feature.list.databinding.ItemListNoteBinding

class NoteListAdapter(
    private val onClickListener: (String) -> Unit,
    private val onRemoveListener: (String) -> Unit
) : BaseAdapter<Note, NoteListAdapter.NoteListViewHolder>() {

    override fun getLayoutId(viewType: Int) = R.layout.item_list_note

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return NoteListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onClickListener, onRemoveListener)
    }

    class NoteListViewHolder(private val binding: ItemListNoteBinding) :
        BaseViewHolder(binding.root) {

        fun bind(
            item: Note,
            onClickListener: (String) -> Unit,
            onRemoveListener: (String) -> Unit
        ) {
            with(binding) {
                tvTitle.text = item.title
                tvDescription.text = item.description
                root.setOnClickListener {
                    onClickListener.invoke(item.id)
                }
                ivDelete.safeOnClickListener {
                    onRemoveListener.invoke(item.id)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): NoteListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListNoteBinding.inflate(layoutInflater, parent, false)
                return NoteListViewHolder(binding)
            }
        }
    }
}