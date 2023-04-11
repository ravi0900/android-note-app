package org.androidnoteapp.notes.tests.reminders

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.androidnoteapp.notes.data.model.Note
import org.androidnoteapp.notes.data.model.Reminder
import org.androidnoteapp.notes.data.repo.NoteRepository
import org.androidnoteapp.notes.data.repo.ReminderRepository
import org.androidnoteapp.notes.di.NO_SYNC
import java.time.Instant
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class ReminderDeletionTest {
    @Inject @Named(NO_SYNC)
    lateinit var noteRepository: NoteRepository
    @Inject
    lateinit var reminderRepository: ReminderRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    @Throws(Exception::class)
    fun deletingANoteShouldAlsoDeleteItsReminders() = runBlocking {
        val note = Note(
            title = "Test Note",
            content = "Sample content"
        )
        val noteId = noteRepository.insertNote(note)

        val reminder = Reminder(
            name = "Test Reminder",
            noteId = noteId,
            date = Instant.now().epochSecond
        )

        reminderRepository.insert(reminder)
        noteRepository.deleteNotes(note.copy(id = noteId))

        assertTrue(reminderRepository.getAll().first().isEmpty())
    }
}
