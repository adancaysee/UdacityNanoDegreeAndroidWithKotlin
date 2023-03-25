package com.udacity.todo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.data.source.TasksRepository
import kotlinx.coroutines.runBlocking

class FakeTasksRepository : TasksRepository {

    var fakeTasks = mutableListOf<Task>()

    private val observableTasks = MutableLiveData<List<Task>?>()

    override fun observeTasks(filterType: TasksFilterType): LiveData<List<Task>?> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    override suspend fun getTasks(forceUpdate: Boolean): List<Task> {
        return fakeTasks
    }

    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override fun observeTask(taskId: String): LiveData<Task?> {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Task? {
        return fakeTasks.find { it.id == taskId }
    }

    override suspend fun refreshTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun saveTask(task: Task) {
        fakeTasks.add(task)
    }

    override suspend fun completeTask(task: Task) {
        refreshTasks()
        val index = fakeTasks.withIndex().first { it.value.id == task.id }.index
        if (index != -1) {
            val currentTask = fakeTasks[index].copy(isCompleted = true)
            fakeTasks[index] = currentTask
        }
    }

    override suspend fun activeTask(task: Task) {
        refreshTasks()
        val index = fakeTasks.withIndex().first { it.value.id == task.id }.index
        if (index != -1) {
            val currentTask = fakeTasks[index].copy(isCompleted = false)
            fakeTasks[index] = currentTask
        }
    }

    override suspend fun deleteTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteCompletedTasks(): Result<Int> {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteTasks() {
        throw NotImplementedError("Unused in tests")
    }

    fun addTasks(vararg tasks: Task) {
        fakeTasks.addAll(tasks)
        runBlocking { refreshTasks() }
    }
}