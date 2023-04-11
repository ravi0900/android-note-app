package org.androidnoteapp.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.androidnoteapp.notes.data.dao.IdMappingDao
import org.androidnoteapp.notes.data.dao.NoteDao
import org.androidnoteapp.notes.data.dao.NoteTagDao
import org.androidnoteapp.notes.data.dao.NotebookDao
import org.androidnoteapp.notes.data.dao.ReminderDao
import org.androidnoteapp.notes.data.dao.TagDao
import org.androidnoteapp.notes.data.model.IdMapping
import org.androidnoteapp.notes.data.model.NoteEntity
import org.androidnoteapp.notes.data.model.NoteTagJoin
import org.androidnoteapp.notes.data.model.Notebook
import org.androidnoteapp.notes.data.model.Reminder
import org.androidnoteapp.notes.data.model.Tag

@Database(
    entities = [
        NoteEntity::class,
        NoteTagJoin::class,
        Notebook::class,
        Tag::class,
        Reminder::class,
        IdMapping::class,
    ],
    version = 1,
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val notebookDao: NotebookDao
    abstract val noteTagDao: NoteTagDao
    abstract val tagDao: TagDao
    abstract val reminderDao: ReminderDao
    abstract val idMappingDao: IdMappingDao

    companion object {
        const val DB_NAME = "notes_database"
    }
}
