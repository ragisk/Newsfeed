package com.example.newsfeed

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SyncNewsWorker(context: Context, workerParams: WorkerParameters)
    : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("SyncNewsfeed", "doWork: started")

                val repository = EntryPointAccessors.fromApplication(
                    applicationContext,
                    RoomRepositoryEntryPoint::class.java
                ).getRoomRepository()
                repository.refreshArticles()
                Log.d("SyncNewsfeed", "doWork: completed")
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}