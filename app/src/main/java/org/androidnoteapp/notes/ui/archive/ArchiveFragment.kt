package org.androidnoteapp.notes.ui.archive

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
import dagger.hilt.android.AndroidEntryPoint
import org.androidnoteapp.notes.R
import org.androidnoteapp.notes.databinding.FragmentArchiveBinding
import org.androidnoteapp.notes.databinding.LayoutNoteBinding
import org.androidnoteapp.notes.ui.common.AbstractNotesFragment
import org.androidnoteapp.notes.ui.utils.navigateSafely
import org.androidnoteapp.notes.ui.utils.viewBinding

@AndroidEntryPoint
class ArchiveFragment : AbstractNotesFragment(R.layout.fragment_archive) {
    private val binding by viewBinding(FragmentArchiveBinding::bind)

    override val currentDestinationId: Int = R.id.fragment_archive
    override val model: ArchiveViewModel by viewModels()

    override val recyclerView: RecyclerView
        get() = binding.recyclerArchive
    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = binding.layoutSwipeRefresh
    override val snackbarLayout: View
        get() = binding.layoutCoordinator
    override val emptyIndicator: View
        get() = binding.indicatorArchiveEmpty
    override val appBarLayout: AppBarLayout
        get() = binding.layoutAppBar.appBar
    override val toolbar: Toolbar
        get() = binding.layoutAppBar.toolbar
    override val toolbarTitle: String
        get() = getString(R.string.nav_archive)
    override val secondaryToolbar: Toolbar
        get() = binding.layoutAppBar.toolbarSelection
    override val secondaryToolbarMenuRes: Int = R.menu.archive_selected_notes

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.archive, menu)
        mainMenu = menu
        setHiddenNotesItemActionText()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> findNavController().navigateSafely(ArchiveFragmentDirections.actionArchiveToSearch())
            R.id.action_show_hidden_notes -> toggleHiddenNotes()
            R.id.action_select_all -> selectAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNoteClick(noteId: Long, position: Int, viewBinding: LayoutNoteBinding) {
        applyNavToEditorAnimation(position)
        findNavController().navigateSafely(
            ArchiveFragmentDirections.actionArchiveToEditor("editor_$noteId").setNoteId(noteId),
            FragmentNavigatorExtras(viewBinding.root to "editor_$noteId")
        )
    }

    override fun onNoteLongClick(noteId: Long, position: Int, viewBinding: LayoutNoteBinding): Boolean {
        showMenuForNote(position)
        return true
    }
}
