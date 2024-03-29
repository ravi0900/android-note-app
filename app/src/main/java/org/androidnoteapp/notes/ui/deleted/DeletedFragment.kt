package org.androidnoteapp.notes.ui.deleted

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import org.androidnoteapp.notes.R
import org.androidnoteapp.notes.databinding.FragmentDeletedBinding
import org.androidnoteapp.notes.databinding.LayoutNoteBinding
import org.androidnoteapp.notes.ui.common.AbstractNotesFragment
import org.androidnoteapp.notes.ui.common.AbstractNotesViewModel
import org.androidnoteapp.notes.ui.common.BaseDialog
import org.androidnoteapp.notes.ui.utils.navigateSafely
import org.androidnoteapp.notes.ui.utils.viewBinding

class DeletedFragment : AbstractNotesFragment(R.layout.fragment_deleted) {
    private val binding by viewBinding(FragmentDeletedBinding::bind)

    override val currentDestinationId: Int = R.id.fragment_deleted
    override val model: DeletedViewModel by viewModels()

    override val recyclerView: RecyclerView
        get() = binding.recyclerDeleted
    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = binding.layoutSwipeRefresh
    override val snackbarLayout: View
        get() = binding.layoutCoordinator
    override val emptyIndicator: View
        get() = binding.indicatorDeletedEmpty
    override val appBarLayout: AppBarLayout
        get() = binding.layoutAppBar.appBar
    override val toolbar: Toolbar
        get() = binding.layoutAppBar.toolbar
    override val toolbarTitle: String
        get() = getString(R.string.nav_deleted)
    override val secondaryToolbar: Toolbar
        get() = binding.layoutAppBar.toolbarSelection
    override val secondaryToolbarMenuRes = R.menu.deleted_selected_notes

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.deleted, menu)
        mainMenu = menu
        setHiddenNotesItemActionText()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_empty_bin -> showEmptyBinDialog()
            R.id.action_show_hidden_notes -> toggleHiddenNotes()
            R.id.action_select_all -> selectAllNotes()
            R.id.action_search -> findNavController().navigateSafely(DeletedFragmentDirections.actionDeletedToSearch())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDataChanged(data: AbstractNotesViewModel.Data) {
        super.onDataChanged(data)

        val days = data.noteDeletionTimeInDays

        binding.indicatorDeletedEmptyText.text =
            if (days != 0L) getString(R.string.indicator_deleted_empty, days) else getString(R.string.indicator_bin_disabled)
    }

    override fun onNoteClick(noteId: Long, position: Int, viewBinding: LayoutNoteBinding) {
        applyNavToEditorAnimation(position)
        findNavController().navigateSafely(
            DeletedFragmentDirections.actionDeletedToEditor("editor_$noteId").setNoteId(noteId),
            FragmentNavigatorExtras(viewBinding.root to "editor_$noteId")
        )
    }

    override fun onNoteLongClick(noteId: Long, position: Int, viewBinding: LayoutNoteBinding): Boolean {
        showMenuForNote(position)
        return true
    }

    private fun showEmptyBinDialog() {
        BaseDialog.build(requireContext()) {
            setTitle(R.string.empty_bin_warning_title)
            setMessage(R.string.empty_bin_warning_text)
            setPositiveButton(R.string.yes) { di, _ ->
                model.permanentlyDeleteNotesInBin()
                di.dismiss()
            }
            setNegativeButton(R.string.no) { di, _ -> di.dismiss() }
        }
            .show()
    }
}
