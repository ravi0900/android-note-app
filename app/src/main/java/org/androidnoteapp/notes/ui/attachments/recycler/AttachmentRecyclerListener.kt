package org.androidnoteapp.notes.ui.attachments.recycler

import org.androidnoteapp.notes.databinding.LayoutAttachmentBinding

interface AttachmentRecyclerListener {
    fun onItemClick(position: Int, viewBinding: LayoutAttachmentBinding)
    fun onLongClick(position: Int, viewBinding: LayoutAttachmentBinding): Boolean
}
