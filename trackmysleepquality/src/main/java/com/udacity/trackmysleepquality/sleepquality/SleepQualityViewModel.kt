package com.udacity.trackmysleepquality.sleepquality


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.trackmysleepquality.database.SleepDao
import kotlinx.coroutines.launch


class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    private val sleepDao: SleepDao
) : ViewModel() {

    private val _navigateToSleepTrackerEvent = MutableLiveData<Boolean>()
    val navigateToSleepTrackerEvent: LiveData<Boolean>
        get() = _navigateToSleepTrackerEvent


    fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
            val tonight = sleepDao.get(sleepNightKey) ?: return@launch

            tonight.sleepQuality = quality
            sleepDao.update(tonight)
            _navigateToSleepTrackerEvent.value = true
        }
    }

    /*
    You should reset event variables. This is our pattern
     */
    fun doneNavigating() {
        _navigateToSleepTrackerEvent.value = false
    }


}