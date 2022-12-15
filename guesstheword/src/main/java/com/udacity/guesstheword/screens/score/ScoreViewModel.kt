package com.udacity.guesstheword.screens.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: MutableLiveData<Int>
        get() = _score

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: MutableLiveData<Boolean>
        get() = _eventPlayAgain


    init {
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.postValue(true)
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.postValue(false)
    }
}