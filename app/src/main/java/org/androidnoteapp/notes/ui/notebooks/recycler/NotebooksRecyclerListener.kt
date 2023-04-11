package org.androidnoteapp.notes.ui.notebooks.recycler

interface NotebooksRecyclerListener {
    fun onItemClick(position: Int)
    fun onLongClick(position: Int): Boolean
}
