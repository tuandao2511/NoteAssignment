package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder>() :
    RecyclerView.Adapter<VH>() {

    val items = mutableListOf<T>()

    protected abstract fun getLayoutId(viewType: Int): Int

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun setListItem(list: List<T>?) {
        items.clear()
        list?.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    fun addItemAtIndexAndNotify(index: Int, item: T) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun addItemAndNotify(item: T) {
        items.add(item)
        if (items.size > 0) {
            notifyItemInserted(items.size - 1)
        }
    }

    fun removeItemAtIndexAndNotify(index: Int) {
        if (items.size > index) items.removeAt(index)
        notifyItemRemoved(index)
    }

    open fun getItem(position: Int): T? = items.getOrNull(position)
}

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

fun createView(parent: ViewGroup, @LayoutRes layoutId: Int): View {
    return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
}
