package com.udacity.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.trackmysleepquality.database.SleepDao
import com.udacity.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    private val sleepDao: SleepDao,
    application: Application
) : AndroidViewModel(application) {

    val nights = sleepDao.getAllNights()

    private var tonight = MutableLiveData<SleepNight?>()

    val startButtonEnable = Transformations.map(tonight) {
        it == null
    }

    val stopButtonEnable = Transformations.map(tonight) {
        it != null
    }

    val clearButtonEnable = Transformations.map(nights) {
        it.isNotEmpty()
    }

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    private val _navigateToSleepQualityEvent = MutableLiveData<SleepNight?>()
    val navigateToSleepQualityEvent: LiveData<SleepNight?>
        get() = _navigateToSleepQualityEvent

    private val _navigateToSleepDetailEvent = MutableLiveData<Long?>()
    val navigateToSleepDetailEvent: LiveData<Long?>
        get() = _navigateToSleepDetailEvent


    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = sleepDao.getTonight()
        if (night?.startTimeMilli != night?.endTimeMilli) {
            night = null
        }
        return night
    }

    private suspend fun insert(night: SleepNight) {
        sleepDao.insert(night)
    }

    private suspend fun update(night: SleepNight) {
        sleepDao.update(night)
    }

    private suspend fun clear() {
        sleepDao.clear()
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val night = SleepNight()
            insert(night)
            tonight.value = getTonightFromDatabase()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch

            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)
            tonight.value = null
            _navigateToSleepQualityEvent.value = oldNight
        }
    }

    fun doneNavigatingToSleepQuality() {
        _navigateToSleepQualityEvent.value = null
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDetailEvent.value = id
    }

    fun doneNavigatingToSleepDetail() {
        _navigateToSleepDetailEvent.value = null
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
            _showSnackbarEvent.value = true
        }
    }

}