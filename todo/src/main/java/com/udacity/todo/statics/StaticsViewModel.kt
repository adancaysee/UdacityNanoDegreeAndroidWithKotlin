package com.udacity.todo.statics

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udacity.todo.TodoApplication
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.data.source.TasksRepository
import kotlinx.coroutines.launch

class StaticsViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val tasks: LiveData<List<Task>?> =
        tasksRepository.observeTasks(TasksFilterType.ALL_TASKS)

    private val statics = Transformations.map(tasks) {
        getActiveAndCompletedStats(it)
    }

    val activeTasksPercent = Transformations.map(statics) {
        it?.activeTasksPercent ?: 0f
    }
    val completedTasksPercent: LiveData<Float> = Transformations.map(statics) {
        it?.completedTasksPercent ?: 0f
    }

    val empty: LiveData<Boolean> = Transformations.map(tasks) {
        it.isNullOrEmpty()
    }
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading = _dataLoading

    fun refresh() {
        _dataLoading.value = true
        viewModelScope.launch {
            tasksRepository.refreshTasks()
            _dataLoading.value = false
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TodoApplication
                StaticsViewModel(application.tasksRepository)
            }
        }
    }
}