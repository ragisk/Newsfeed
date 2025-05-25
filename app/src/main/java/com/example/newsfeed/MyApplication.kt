package com.example.newsfeed

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){


    override fun onCreate() {
        super.onCreate()
//        SyncScheduler.schedule(applicationContext)
    }
}

