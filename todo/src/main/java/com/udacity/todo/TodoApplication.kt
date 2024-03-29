package com.udacity.todo

import android.app.Application
import com.udacity.todo.data.source.TasksRepository
import timber.log.Timber

class TodoApplication : Application() {

    val tasksRepository: TasksRepository
        get() = ServiceLocator.providesTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }
}