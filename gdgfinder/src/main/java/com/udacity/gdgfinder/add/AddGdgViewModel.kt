package com.udacity.gdgfinder.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddGdgViewModel : ViewModel() {

    private val _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent:LiveData<Boolean>
    get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    fun onSubmitApplication() {
        _showSnackbarEvent.value = true
    }
}