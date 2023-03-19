package com.udacity.todo.tasklist

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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
 *  The function passed to switchMap() must return a LiveData object. Bir livedata değişikliği başka bir livedata ile sonuçlanıcak
 */

class TaskListViewModel(
    private val application: Application,
    private val tasksRepository: TasksRepository
) : AndroidViewModel(application) {

    private var currentFilteringType = MutableLiveData(TasksFilterType.ALL_TASKS)
    private val _tasks: LiveData<List<Task>> =
        Transformations.switchMap(currentFilteringType) { filterType ->
            setFiltering(filterType)
            tasksRepository.observeTasks(filterType)
        }

    val tasks: LiveData<List<Task>> = _tasks
    val empty = Transformations.map(_tasks) { it.isNullOrEmpty() }

    private val _currentFilteringLabel = MutableLiveData<String>()
    val currentFilteringLabel: LiveData<String> = _currentFilteringLabel

    private val _noTasksLabel = MutableLiveData<String>()
    val noTasksLabel: LiveData<String> = _noTasksLabel

    private val _noTaskIconRes = MutableLiveData<Drawable>()
    val noTaskIconRes: LiveData<Drawable> = _noTaskIconRes

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _newTaskEvent = MutableLiveData<Event<Unit>>()
    val newTaskEvent: LiveData<Event<Unit>>
        get() = _newTaskEvent

    private val _openTaskEvent = MutableLiveData<Event<String>>()
    val openTaskEvent: LiveData<Event<String>> = _openTaskEvent

    private val _snackbarTextEvent = MutableLiveData<Event<String>>()
    val snackbarTextEvent = _snackbarTextEvent


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

    private fun setFiltering(filterType: TasksFilterType) {
        when (filterType) {
            TasksFilterType.ALL_TASKS -> {
                setFilter(
                    R.string.label_all, R.string.no_tasks_all,
                    R.drawable.logo_no_fill
                )
            }
            TasksFilterType.ACTIVE_TASKS -> {
                setFilter(
                    R.string.label_active, R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp
                )
            }
            TasksFilterType.COMPLETED_TASKS -> {
                setFilter(
                    R.string.label_completed, R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp
                )
            }
        }
    }

    private fun setFilter(
        @StringRes filteringLabelString: Int, @StringRes noTasksLabelString: Int,
        @DrawableRes noTaskIconDrawable: Int
    ) {
        _currentFilteringLabel.value = application.getString(filteringLabelString)
        _noTasksLabel.value = application.getString(noTasksLabelString)
        _noTaskIconRes.value = ContextCompat.getDrawable(application, noTaskIconDrawable)
    }

    fun changeTaskActivateStatus(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            if (isCompleted) {
                tasksRepository.completeTask(task)
                showSnackbarMessage(application.getString(R.string.task_marked_complete))
            } else {
                tasksRepository.activeTask(task)
                showSnackbarMessage(application.getString(R.string.task_marked_active))
            }
        }
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            val result = tasksRepository.deleteCompletedTasks()
            if (result is Result.Success) {
                showSnackbarMessage(
                    application.getString(
                        R.string.completed_tasks_cleared,
                        result.data
                    )
                )
            } else if (result is Result.Error) {
                showSnackbarMessage(result.exception.message ?: "Error found")
            }

        }
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarTextEvent.value = Event(message)
    }

    fun showResultMessage(resultMessage: String?) {
        if (resultMessage.isNullOrEmpty()) return
        showSnackbarMessage(resultMessage)
        /*when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(application.getString(R.string.successfully_saved_task_message))
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(application.getString(R.string.successfully_added_task_message))
            DELETE_RESULT_OK -> showSnackbarMessage(application.getString(R.string.successfully_deleted_task_message))
        }*/
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
                TaskListViewModel(application, application.tasksRepository)
            }
        }
    }
}