package com.udacity.todo.addedittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddEditTaskViewModel : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()
}