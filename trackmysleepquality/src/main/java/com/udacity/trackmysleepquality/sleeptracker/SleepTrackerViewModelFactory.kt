package com.udacity.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.trackmysleepquality.database.SleepDao


class SleepTrackerViewModelFactory(
    private val sleepDao: SleepDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(sleepDao,application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}