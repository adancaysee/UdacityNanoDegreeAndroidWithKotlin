package com.udacity.todo.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuProviderViewModel : ViewModel() {

    val selectDrawerMenu = MutableLiveData(false)

    fun doneSelection() {
        selectDrawerMenu.value = false
    }
}