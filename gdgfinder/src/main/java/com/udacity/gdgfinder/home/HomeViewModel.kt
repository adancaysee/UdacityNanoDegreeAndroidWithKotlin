package com.udacity.gdgfinder.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _navigateToSearchEvent = MutableLiveData<Boolean>()
    val navigateToSearchEvent: LiveData<Boolean>
        get() = _navigateToSearchEvent

    fun onFabClicked() {
        _navigateToSearchEvent.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearchEvent.value = false
    }
}