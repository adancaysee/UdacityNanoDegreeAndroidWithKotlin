package com.udacity.todo.addedittask

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.todo.R
import com.udacity.todo.TodoApplication
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksRepository
import com.udacity.todo.util.Event
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    private val application: Application,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    private var taskId: String? = null

    private var isTaskCompleted = false

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    var isDataLoaded = false

    private val _showSnackbarEvent = MutableLiveData<Event<String>>()
    val showSnackbarEvent: LiveData<Event<String>> = _showSnackbarEvent

    private val _resultMessageEvent = MutableLiveData<Event<String>>()
    val resultMessageEvent = _resultMessageEvent

    fun setTaskId(taskId: String?) {
        if (_dataLoading.value == true) return

        if (isDataLoaded) return

        this.taskId = taskId

        if (taskId == null) return

        _dataLoading.value = true
        viewModelScope.launch {
            val task = tasksRepository.getTask(taskId)
            if (task != null) {
                loadTask(task)
            }
            _dataLoading.value = false
        }
    }

    private fun loadTask(task: Task) {
        title.value = task.title
        description.value = task.description
        isDataLoaded = true
    }

    fun saveTask() {
        val currentTaskId = taskId
        val currentTitle = title.value
        val currentDescription = description.value
        if (currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty()) {
            _showSnackbarEvent.value = Event(application.getString(R.string.empty_task_message))
            return
        }
        viewModelScope.launch {
            if (currentTaskId != null) {
                val task = Task(currentTitle, currentDescription, isTaskCompleted,currentTaskId)
                updateTask(task)
            } else {
                createTask(currentTitle, currentDescription)
            }
        }

    }

    private suspend fun createTask(title: String, description: String) {
        val newTask = Task(title = title, description = description, isCompleted = false)
        tasksRepository.saveTask(newTask)
        _resultMessageEvent.value =
            Event(application.getString(R.string.successfully_added_task_message))

    }

    private suspend fun updateTask(task: Task) {
        tasksRepository.saveTask(task)
        _resultMessageEvent.value =
            Event(application.getString(R.string.successfully_saved_task_message))
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TodoApplication
                AddEditTaskViewModel(
                    application,
                    application.tasksRepository
                )
            }
        }
    }
}