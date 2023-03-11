package com.udacity.firebaseuiloginsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<User?>(null)
    val user: LiveData<User?>
        get() = _user


    fun login() {

    }

}