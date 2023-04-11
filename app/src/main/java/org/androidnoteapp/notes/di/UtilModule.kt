package org.androidnoteapp.notes.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.androidnoteapp.notes.App
import org.androidnoteapp.notes.BuildConfig
import org.androidnoteapp.notes.components.MediaStorageManager
import org.androidnoteapp.notes.components.backup.BackupManager
import org.androidnoteapp.notes.data.repo.IdMappingRepository
import org.androidnoteapp.notes.data.repo.NoteRepository
import org.androidnoteapp.notes.data.repo.NotebookRepository
import org.androidnoteapp.notes.data.repo.ReminderRepository
import org.androidnoteapp.notes.data.repo.TagRepository
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.data.sync.nextcloud.NextcloudManager
import org.androidnoteapp.notes.preferences.PreferenceRepository
import org.androidnoteapp.notes.ui.reminders.ReminderManager
import org.androidnoteapp.notes.ui.utils.ConnectionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideMediaStorageManager(
        @ApplicationContext context: Context,
        noteRepository: NoteRepository,
    ) = MediaStorageManager(context, noteRepository, App.MEDIA_FOLDER)

    @Provides
    @Singleton
    fun provideReminderManager(
        @ApplicationContext context: Context,
        reminderRepository: ReminderRepository,
    ) = ReminderManager(context, reminderRepository)

    @Provides
    @Singleton
    fun provideSyncManager(
        @ApplicationContext context: Context,
        preferenceRepository: PreferenceRepository,
        idMappingRepository: IdMappingRepository,
        nextcloudManager: NextcloudManager,
        app: Application,
    ) = SyncManager(
        preferenceRepository,
        idMappingRepository,
        ConnectionManager(context),
        nextcloudManager,
        (app as App).syncingScope
    )

    @Provides
    @Singleton
    fun provideBackupManager(
        noteRepository: NoteRepository,
        notebookRepository: NotebookRepository,
        tagRepository: TagRepository,
        reminderRepository: ReminderRepository,
        idMappingRepository: IdMappingRepository,
        reminderManager: ReminderManager,
        @ApplicationContext context: Context,
    ) = BackupManager(
        BuildConfig.VERSION_CODE,
        noteRepository,
        notebookRepository,
        tagRepository,
        reminderRepository,
        idMappingRepository,
        reminderManager,
        context
    )
}
