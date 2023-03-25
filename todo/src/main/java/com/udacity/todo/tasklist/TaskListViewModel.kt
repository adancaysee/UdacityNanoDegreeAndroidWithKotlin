package com.udacity.todo.tasklist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.todo.*
import com.udacity.todo.data.Result
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.data.source.TasksRepository
import com.udacity.todo.util.Event
import kotlinx.coroutines.launch

/**
 *  The function passed to switchMap() must return a LiveData object. "Bir livedata değişikliği başka bir livedata ile sonuçlanıcak"
 */

class TaskListViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private var currentFilteringType = MutableLiveData(TasksFilterType.ALL_TASKS)
    private val _tasks: LiveData<List<Task>> =
        Transformations.switchMap(currentFilteringType) { filterType ->
            setFiltering(filterType)
            tasksRepository.observeTasks(filterType)
        }

    val tasks: LiveData<List<Task>> = _tasks
    val empty = Transformations.map(_tasks) { it.isNullOrEmpty() }

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel: LiveData<Int> = _noTasksLabel

    private val _noTaskIconRes = MutableLiveData<Int>()
    val noTaskIconRes: LiveData<Int> = _noTaskIconRes

    private val _tasksAddViewVisible = MutableLiveData<Boolean>()
    val tasksAddViewVisible: LiveData<Boolean> = _tasksAddViewVisible

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _newTaskEvent = MutableLiveData<Event<Unit>>()
    val newTaskEvent: LiveData<Event<Unit>>
        get() = _newTaskEvent

    private val _openTaskEvent = MutableLiveData<Event<String>>()
    val openTaskEvent: LiveData<Event<String>> = _openTaskEvent

    private val _snackbarTextEvent = MutableLiveData<Event<String>>()
    val snackbarTextEvent = _snackbarTextEvent
    private val _snackbarIntEvent = MutableLiveData<Event<Int>>()
    val snackbarIntEvent = _snackbarIntEvent


    init {
        refreshTasks()
    }

    fun refreshTasks() {
        _dataLoading.value = true
        viewModelScope.launch {
            tasksRepository.refreshTasks()
            _dataLoading.value = false
        }
    }

    fun filterTasks(filterType: TasksFilterType) {
        currentFilteringType.value = filterType
    }

    fun setFiltering(filterType: TasksFilterType) {
        when (filterType) {
            TasksFilterType.ALL_TASKS -> {
                setFilter(
                    R.string.label_all, R.string.no_tasks_all,
                    R.drawable.logo_no_fill,
                    true
                )
            }
            TasksFilterType.ACTIVE_TASKS -> {
                setFilter(
                    R.string.label_active, R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp,
                    false
                )
            }
            TasksFilterType.COMPLETED_TASKS -> {
                setFilter(
                    R.string.label_completed, R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp,
                    false
                )
            }
        }
    }

    private fun setFilter(
        @StringRes filteringLabelString: Int, @StringRes noTasksLabelString: Int,
        @DrawableRes noTaskIconDrawable: Int, tasksAddVisible: Boolean
    ) {
        _currentFilteringLabel.value = filteringLabelString
        _noTasksLabel.value = noTasksLabelString
        _noTaskIconRes.value = noTaskIconDrawable
        _tasksAddViewVisible.value = tasksAddVisible
    }

    fun changeTaskActivateStatus(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            if (isCompleted) {
                tasksRepository.completeTask(task)
                showSnackbarMessage(R.string.task_marked_complete)
            } else {
                tasksRepository.activeTask(task)
                showSnackbarMessage(R.string.task_marked_active)
            }
        }
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            val result = tasksRepository.deleteCompletedTasks()
            if (result is Result.Success) {
                showSnackbarMessage(R.string.completed_tasks_cleared)
            } else if (result is Result.Error) {
                showSnackbarMessage(result.exception.message ?: "Error found")
            }

        }
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarTextEvent.value = Event(message)
    }

    private fun showSnackbarMessage(resourceId:Int) {
        _snackbarIntEvent.value = Event(resourceId)
    }

    fun showResultMessage(resultMessage: String?) {
        if (resultMessage.isNullOrEmpty()) return
        showSnackbarMessage(resultMessage)
    }

    fun navigateToNewTask() {
        _newTaskEvent.value = Event(Unit)
    }

    fun navigateToTaskDetail(taskId: String) {
        _openTaskEvent.value = Event(taskId)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TodoApplication
                TaskListViewModel(application.tasksRepository)
            }
        }
    }
}