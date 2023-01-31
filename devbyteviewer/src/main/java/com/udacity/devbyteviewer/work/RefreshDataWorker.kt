package com.udacity.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.devbyteviewer.database.DevByteDatabase
import com.udacity.devbyteviewer.network.DevByteApi
import com.udacity.devbyteviewer.repository.DefaultVideosRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, param: WorkerParameters) :
    CoroutineWorker(appContext, param) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val localVideosDataSource = DevByteDatabase.getInstance(applicationContext).dao
        val repository =
            DefaultVideosRepository(localVideosDataSource, DevByteApi.remoteVideosDataSource)
        return try {
            repository.refreshVideos()
            Result.success()
        } catch (ex: HttpException) {
            Result.retry()
        }
    }
}