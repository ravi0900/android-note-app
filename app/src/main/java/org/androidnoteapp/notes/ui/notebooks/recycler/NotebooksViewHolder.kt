package org.androidnoteapp.notes.ui.notebooks.recycler

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import org.androidnoteapp.notes.data.model.Notebook
import org.androidnoteapp.notes.databinding.LayoutNotebookBinding
import org.androidnoteapp.notes.ui.common.recycler.SelectableViewHolder

class NotebooksViewHolder(
    val context: Context,
    val binding: LayoutNotebookBinding,
    listener: NotebooksRecyclerListener?,
) : RecyclerView.ViewHolder(binding.root), SelectableViewHolder {

    init {
        if (listener != null) {
            itemView.setOnClickListener { listener.onItemClick(bindingAdapterPosition) }
            itemView.setOnLongClickListener { listener.onLongClick(bindingAdapterPosition) }
        }
    }

    private fun setName(name: String) {
        binding.textViewNotebookName.text = name
    }

    fun bind(notebook: Notebook) {
        setName(notebook.name)
    }

    fun runPayloads(notebook: Notebook, payloads: List<NotebooksRecyclerAdapter.Payload>) {
        payloads.forEach {
            when (it) {
                NotebooksRecyclerAdapter.Payload.NameChanged -> setName(notebook.name)
            }
        }
    }

    override fun onSelectedStatusChanged(isSelected: Boolean) {
        binding.root.isSelected = isSelected
    }
}
