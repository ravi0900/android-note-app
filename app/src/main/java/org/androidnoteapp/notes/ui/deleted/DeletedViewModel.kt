package org.androidnoteapp.notes.ui.deleted

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.androidnoteapp.notes.components.MediaStorageManager
import org.androidnoteapp.notes.data.repo.NoteRepository
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.preferences.PreferenceRepository
import org.androidnoteapp.notes.ui.common.AbstractNotesViewModel
import javax.inject.Inject

@HiltViewModel
class DeletedViewModel @Inject constructor(
    private val notesRepository: NoteRepository,
    private val mediaStorageManager: MediaStorageManager,
    preferenceRepository: PreferenceRepository,
    syncManager: SyncManager,
) : AbstractNotesViewModel(preferenceRepository, syncManager) {

    override val provideNotes = notesRepository::getDeleted

    fun permanentlyDeleteNotesInBin() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.permanentlyDeleteNotesInBin()
            mediaStorageManager.cleanUpStorage()
        }
    }
}
