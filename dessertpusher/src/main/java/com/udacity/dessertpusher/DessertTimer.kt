package com.udacity.dessertpusher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import timber.log.Timber

class DessertTimer(lifecycle: Lifecycle) : LifecycleEventObserver {
    var secondCount = 0

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }

    private fun startTimer() {
        Timber.i("Timer started")
        runnable = Runnable {
            secondCount++
            Timber.i("Timer is at : $secondCount")
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
        Timber.i("Timer stopped")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            startTimer()
        }else if (event == Lifecycle.Event.ON_STOP) {
            stopTimer()
        }
    }
}