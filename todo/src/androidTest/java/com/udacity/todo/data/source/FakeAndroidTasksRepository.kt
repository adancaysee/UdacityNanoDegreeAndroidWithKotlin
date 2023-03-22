package com.udacity.todo.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.todo.data.Result
import com.udacity.todo.data.domain.Task
import kotlinx.coroutines.runBlocking

class FakeAndroidTasksRepository : TasksRepository {

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
        runBlocking { refreshTasks() }
        return Transformations.map(observableTasks) { tasks ->
            tasks?.firstOrNull { it.id == taskId }
        }
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Task? {
        return fakeTasks.firstOrNull { it.id == taskId }
    }

    override suspend fun refreshTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun saveTask(task: Task) {
        fakeTasks.add(task)
    }

    override suspend fun completeTask(task: Task) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun activeTask(task: Task) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteCompletedTasks(): Result<Int> {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteTasks() {
        fakeTasks.clear()
    }

    fun addTasks(vararg tasks: Task) {
        fakeTasks.addAll(tasks)
        runBlocking { refreshTasks() }
    }
}