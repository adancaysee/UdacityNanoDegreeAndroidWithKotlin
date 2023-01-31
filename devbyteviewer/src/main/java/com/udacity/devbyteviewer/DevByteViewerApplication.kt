package com.udacity.devbyteviewer

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.devbyteviewer.repository.AppContainer
import com.udacity.devbyteviewer.repository.DefaultAppContainer
import com.udacity.devbyteviewer.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class DevByteViewerApplication : Application() {

    lateinit var appContainer: AppContainer

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContainer = DefaultAppContainer(this)

        applicationScope.launch {
            setupRecurringWork()
        }

    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            1, TimeUnit.DAYS
        ).setConstraints(constraints).build()

        /**
         * enqueueUniquePeriodicWork -- to schedule the work
         */
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, /** When two request for the same unique name, old one keep, discard new request */
            periodicWorkRequest
        )
    }
}

/**
 PeriodicWorkRequest.Builder(RefreshDataWorker::class.java,1,TimeUnit.DAYS)
 ==
 PeriodicWorkRequestBuilder<RefreshDataWorker>(1,TimeUnit.DAYS)
 */