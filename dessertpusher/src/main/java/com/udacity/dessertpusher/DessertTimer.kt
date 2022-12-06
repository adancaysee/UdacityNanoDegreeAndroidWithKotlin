package com.udacity.dessertpusher

import android.os.Handler
import android.os.Looper
import timber.log.Timber

class DessertTimer {
    var secondCount = 0

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    fun startTimer() {
        runnable = Runnable {
            secondCount++
            Timber.i("Timer is at : $secondCount")
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)
    }

    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }
}