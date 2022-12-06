package com.udacity.dessertpusher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import timber.log.Timber

class DessertTimer(lifecycle: Lifecycle) : DefaultLifecycleObserver {
    var secondCount = 0

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startTimer()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopTimer()
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
}