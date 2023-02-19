package com.udacity.eggtimer.ui

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.*
import com.udacity.eggtimer.R
import com.udacity.eggtimer.receiver.AlarmReceiver
import com.udacity.eggtimer.util.cancelAllNotifications
import com.udacity.eggtimer.util.getAlarmManager
import com.udacity.eggtimer.util.getNotificationManager
import com.udacity.eggtimer.util.getPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A PendingIntent object wraps the functionality of an Intent object while allowing your app to specify
 * something that another app should do, on your app’s behalf, in response to a future action
 */

private const val TRIGGER_TIME = "TRIGGER_AT"
private const val REQUEST_CODE = 0

class EggTimerViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _timeSelection = MutableLiveData<Int>()
    val timeSelection: LiveData<Int>
        get() = _timeSelection

    private var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private val prefs = getPreference(application)
    private val alarmManager = getAlarmManager(application)

    private val minute: Long = 60_000L
    private val second: Long = 1_000L
    private val timerLengthOptions: IntArray
    private lateinit var timer: CountDownTimer

    private val notifyPendingIntent: PendingIntent


    init {
        val notifyIntent = Intent(application, AlarmReceiver::class.java)

        val flagNoCreate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        } else {
            PendingIntent.FLAG_NO_CREATE
        }

        _alarmOn.value = PendingIntent.getBroadcast(
            application,
            REQUEST_CODE,
            notifyIntent,
            flagNoCreate
        ) != null

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        notifyPendingIntent = PendingIntent.getBroadcast(
            application,
            REQUEST_CODE,
            notifyIntent,
            flag
        )

        timerLengthOptions = application.resources.getIntArray(R.array.minutes_array)

        if (_alarmOn.value!!) {
            createTimer()
        }


    }

    fun setSelectedTime(timerLengthSelection: Int) {
        _timeSelection.value = timerLengthSelection
    }

    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> _timeSelection.value?.let { startTimer(it) }
            false -> cancel()
        }
    }


    private fun startTimer(timerLengthSelection: Int) {
        _alarmOn.value?.let {
            if (!it) {
                _alarmOn.value = true
                val selectedInterval = when (timerLengthSelection) {
                    0 -> second * 10
                    else -> timerLengthOptions[timerLengthSelection] * minute
                }

                val triggerTime = SystemClock.elapsedRealtime() + selectedInterval

                //cancel all previous notifications
                getNotificationManager(application.applicationContext).cancelAllNotifications()

                //set an alarm
                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    notifyPendingIntent
                )
                viewModelScope.launch {
                    saveTime(triggerTime)
                }
            }
        }
        createTimer()
    }

    private fun createTimer() {
        viewModelScope.launch {
            val triggerTime = loadTime()
            timer = object : CountDownTimer(triggerTime, second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            timer.start()
        }
    }

    private fun cancel() {
        resetTimer()
        alarmManager.cancel(notifyPendingIntent)
    }

    private fun resetTimer() {
        timer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    private suspend fun saveTime(triggerTime: Long) = withContext(Dispatchers.IO) {
        prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
    }

    private suspend fun loadTime(): Long = withContext(Dispatchers.IO) {
        prefs.getLong(TRIGGER_TIME, 0)
    }
}