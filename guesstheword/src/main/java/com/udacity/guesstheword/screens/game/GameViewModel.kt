package com.udacity.guesstheword.screens.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 10000L //one minute
    }

    private lateinit var wordList: MutableList<String>

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: MutableLiveData<Boolean>
        get() = _eventGameFinish

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: MutableLiveData<Long>
        get() = _currentTime

    private var timer: CountDownTimer


    init {
        Timber.i("GameViewModel created")
        resetList()
        nextWord()
        _score.postValue(0)

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.postValue(millisUntilFinished/ ONE_SECOND)

            }

            override fun onFinish() {
                _currentTime.postValue(DONE)
                _eventGameFinish.postValue(true)
            }

        }
        timer.start()
    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.postValue(wordList.removeAt(0))
    }

    fun onGameFinishComplete() {
        _eventGameFinish.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("GameViewModel destroyed")
        timer.cancel()
    }

}