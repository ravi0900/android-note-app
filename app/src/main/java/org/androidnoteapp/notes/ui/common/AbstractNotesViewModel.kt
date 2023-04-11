package org.androidnoteapp.notes.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.msoul.datastore.defaultOf
import org.androidnoteapp.notes.data.model.Note
import org.androidnoteapp.notes.data.sync.core.Success
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.preferences.LayoutMode
import org.androidnoteapp.notes.preferences.NoteDeletionTime
import org.androidnoteapp.notes.preferences.PreferenceRepository
import org.androidnoteapp.notes.preferences.SortMethod

abstract class AbstractNotesViewModel(
    protected val preferenceRepository: PreferenceRepository,
    protected val syncManager: SyncManager,
) : ViewModel() {

    protected abstract val provideNotes: (SortMethod) -> Flow<List<Note>>

    @OptIn(ExperimentalCoroutinesApi::class)
    val data = preferenceRepository.getAll()
        .flatMapLatest { prefs ->
            provideNotes(prefs.sortMethod).map { notes ->
                Data(notes, prefs.sortMethod, prefs.layoutMode, prefs.noteDeletionTime.toDays())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Data())

    suspend fun isSyncingEnabled(): Boolean = syncManager.ifSyncing { _, _ -> Success } == Success

    data class Data(
        val notes: List<Note> = emptyList(),
        val sortMethod: SortMethod = defaultOf(),
        val layoutMode: LayoutMode = defaultOf(),
        val noteDeletionTimeInDays: Long = defaultOf<NoteDeletionTime>().toDays(),
    )
}
