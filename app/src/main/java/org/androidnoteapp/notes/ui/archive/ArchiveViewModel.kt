package org.androidnoteapp.notes.ui.archive

import dagger.hilt.android.lifecycle.HiltViewModel
import org.androidnoteapp.notes.data.repo.NoteRepository
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.preferences.PreferenceRepository
import org.androidnoteapp.notes.ui.common.AbstractNotesViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    noteRepository: NoteRepository,
    preferenceRepository: PreferenceRepository,
    syncManager: SyncManager
) : AbstractNotesViewModel(preferenceRepository, syncManager) {
    override val provideNotes = noteRepository::getArchived
}
