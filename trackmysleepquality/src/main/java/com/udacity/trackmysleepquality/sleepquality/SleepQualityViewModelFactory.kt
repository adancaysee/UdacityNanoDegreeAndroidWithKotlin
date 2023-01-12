package com.udacity.trackmysleepquality.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.trackmysleepquality.database.SleepDao


/*
ViewModelFactory --> To use for dependency injection --> provide dependencies to ViewModel
 */

class SleepQualityViewModelFactory(
    private val sleepNightKey:Long,
    private val sleepDao: SleepDao,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey, sleepDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}