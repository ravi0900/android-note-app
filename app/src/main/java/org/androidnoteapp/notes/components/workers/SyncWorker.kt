package org.androidnoteapp.notes.components.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.androidnoteapp.notes.data.sync.core.Success
import org.androidnoteapp.notes.data.sync.core.SyncManager
import org.androidnoteapp.notes.preferences.BackgroundSync
import org.androidnoteapp.notes.preferences.PreferenceRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val preferenceRepository: PreferenceRepository,
    private val syncManager: SyncManager,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        if (preferenceRepository.get<BackgroundSync>().first() == BackgroundSync.DISABLED)
            return@withContext Result.failure()

        when (syncManager.sync()) {
            Success -> Result.success()
            else -> Result.failure()
        }
    }
}
