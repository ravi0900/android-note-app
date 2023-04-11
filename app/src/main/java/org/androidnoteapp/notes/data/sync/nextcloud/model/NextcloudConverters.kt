package org.androidnoteapp.notes.data.sync.nextcloud.model

import org.androidnoteapp.notes.data.model.Note

fun Note.asNextcloudNote(id: Long, category: String): NextcloudNote = NextcloudNote(
    id = id,
    title = title,
    content = if (isList) taskListToString() else content,
    category = category,
    favorite = isPinned,
    modified = modifiedDate
)

fun NextcloudNote.asNewLocalNote(id: Long, notebookId: Long?) = Note(
    id = id,
    title = title,
    content = content,
    isPinned = favorite,
    modifiedDate = modified,
    notebookId = notebookId
)
