package org.androidnoteapp.notes.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.androidnoteapp.notes.R
import org.androidnoteapp.notes.data.repo.NoteRepository
import org.androidnoteapp.notes.data.repo.NotebookRepository
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.preferences.PreferenceRepository
import org.androidnoteapp.notes.preferences.SortMethod
import org.androidnoteapp.notes.ui.common.AbstractNotesViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val notebookRepository: NotebookRepository,
    preferenceRepository: PreferenceRepository,
    syncManager: SyncManager,
) : AbstractNotesViewModel(preferenceRepository, syncManager) {

    private val notebookIdFlow: MutableStateFlow<Long?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val provideNotes = { sortMethod: SortMethod ->
        notebookIdFlow.flatMapLatest { id ->
            when (id) {
                null -> noteRepository.getNonDeletedOrArchived(sortMethod)
                R.id.nav_default_notebook.toLong() -> noteRepository.getNotesWithoutNotebook(sortMethod)
                else -> noteRepository.getByNotebook(id, sortMethod)
            }
        }
    }

    suspend fun notebookExists(notebookId: Long) = notebookRepository.getById(notebookId).firstOrNull() != null

    fun initialize(notebookId: Long?) {
        viewModelScope.launch { notebookIdFlow.emit(notebookId) }
    }
}
