package com.udacity.trackmysleepquality.sleepdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.trackmysleepquality.database.SleepDao

class SleepDetailViewModelFactory(
    private val sleepNightKey:Long,
    private val sleepDao: SleepDao,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightKey, sleepDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
 