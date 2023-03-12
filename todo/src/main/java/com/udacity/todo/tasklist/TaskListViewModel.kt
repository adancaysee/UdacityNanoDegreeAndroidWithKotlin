package com.udacity.todo.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskListViewModel : ViewModel() {

    private val _addTaskEvent = MutableLiveData<Boolean?>()
    val addTaskEvent: LiveData<Boolean?>
        get() = _addTaskEvent

    fun addTask() {
        _addTaskEvent.value = true
    }

    fun doneAddTaskEvent() {
        _addTaskEvent.value = false
    }
}