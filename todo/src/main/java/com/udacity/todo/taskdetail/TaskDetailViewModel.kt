package com.udacity.todo.taskdetail


import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.todo.R
import com.udacity.todo.TodoApplication
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksRepository
import com.udacity.todo.util.Event
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val application: Application,
    private val tasksRepository: TasksRepository,
) : AndroidViewModel(application) {

    private val _taskId = MutableLiveData<String>()

    private val _task = Transformations.switchMap(_taskId) {
        tasksRepository.observeTask(it)
    }
    val task: LiveData<Task?> = _task

    val isDataAvailable: LiveData<Boolean> = Transformations.map(_task) { it != null }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _editTaskEvent = MutableLiveData<Event<Unit>>()
    val editTaskEvent: LiveData<Event<Unit>> = _editTaskEvent

    private val _deleteTaskEvent = MutableLiveData<Event<Unit>>()
    val deleteTaskEvent: LiveData<Event<Unit>> = _deleteTaskEvent

    private val _showSnackbarEvent = MutableLiveData<Event<String>>()
    val showSnackbarEvent: LiveData<Event<String>> = _showSnackbarEvent

    fun setTaskId(taskId: String) {
        if (_taskId.value == taskId && dataLoading.value == true) {
            return
        }
        _taskId.value = taskId
    }

    fun changeTaskActivateStatus(isCompleted: Boolean) {
        viewModelScope.launch {
            val task = _task.value ?: return@launch
            if (isCompleted) {
                tasksRepository.completeTask(task)
                showSnackbarMessage(application.getString(R.string.task_marked_complete))
            } else {
                tasksRepository.activeTask(task)
                showSnackbarMessage(application.getString(R.string.task_marked_active))
            }
        }
    }

    fun refreshTask() {
        _taskId.value?.let {
            _dataLoading.value = true
            viewModelScope.launch {
                tasksRepository.refreshTask(it)
                _dataLoading.value = false
            }
        }

    }

    fun deleteTask() = viewModelScope.launch {
        _taskId.value?.let {
            tasksRepository.deleteTask(it)
            _deleteTaskEvent.value = Event(Unit)
        }
    }

    fun editTask() {
        _editTaskEvent.value = Event(Unit)
    }

    private fun showSnackbarMessage(message: String) {
        _showSnackbarEvent.value = Event(message)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TodoApplication
                TaskDetailViewModel(application, application.tasksRepository)
            }
        }
    }

}