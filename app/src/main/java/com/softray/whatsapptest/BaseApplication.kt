package com.softray.whatsapptest

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BaseApplication : Application() {
    private val applicationCoroutine = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationCoroutine.launch {
            setupWroker()
        }
    }

    private fun setupWroker() {
        val work = PeriodicWorkRequestBuilder<WrokerClass>(20, TimeUnit.MINUTES).build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(WrokerClass.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, work)
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
}