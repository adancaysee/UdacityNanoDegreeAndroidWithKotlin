package com.udacity.trackmysleepquality.sleepdetail

import androidx.lifecycle.*
import com.udacity.trackmysleepquality.database.SleepDao
import com.udacity.trackmysleepquality.database.SleepNight

/**
 * I should handle click on viewModel. (Even if the progress is just navigation)

 ***
 *MediatorLiveData is merger LiveData
 * Option 1:
 * night.addSource(sleepDao.getNightWithId(sleepNightKey), night::setValue)
 * Option 2:
 * night.addSource(sleepDao.getNightWithId(sleepNightKey)) {night.value = it}
 */

class SleepDetailViewModel(
        sleepNightKey: Long = 0L,
        val sleepDao: SleepDao) : ViewModel() {


    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(sleepDao.getNightWithId(sleepNightKey), night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

}

 
