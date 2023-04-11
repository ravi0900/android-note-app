package org.androidnoteapp.notes.ui.common.recycler

import org.androidnoteapp.notes.databinding.LayoutNoteBinding

interface NoteRecyclerListener {
    fun onItemClick(position: Int, viewBinding: LayoutNoteBinding)
    fun onLongClick(position: Int, viewBinding: LayoutNoteBinding): Boolean
}
