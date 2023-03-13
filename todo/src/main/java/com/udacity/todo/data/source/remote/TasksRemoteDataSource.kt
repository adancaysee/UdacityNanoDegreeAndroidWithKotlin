package com.udacity.todo.data.source.remote

import com.udacity.todo.data.Result
import com.udacity.todo.data.source.local.TaskEntity
import kotlinx.coroutines.delay
import timber.log.Timber

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

class TasksRemoteDataSource {

    private var tasksServiceData = ArrayList<TaskEntity>()

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    suspend fun fetchTasks(): Result<List<TaskEntity>> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Result.Success(tasksServiceData)
    }

    suspend fun fetchTask(taskId: String): Result<TaskEntity> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val task = tasksServiceData.find {
            it.id == taskId
        }
        task?.let {
            return Result.Success(task)
        }
        return Result.Error(Exception("Task not found"))
    }

    private fun addTask(title: String, description: String) {
        val newTask = TaskEntity(title = title, description = description)
        tasksServiceData.add(newTask)
    }

    suspend fun saveTask(task: TaskEntity) {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasksServiceData.add(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val index = tasksServiceData.indexOf(task)
        if (index != -1) {
            tasksServiceData[index] = task
        }
    }

    suspend fun deleteTask(taskId: String) {
        delay(SERVICE_LATENCY_IN_MILLIS)
        val task = tasksServiceData.find {
            it.id == taskId
        }
        task?.let {
            tasksServiceData.remove(task)
        }
    }

    suspend fun deleteCompletedTasks() {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasksServiceData.removeAll { it.isCompleted }
    }

    suspend fun deleteAllTasks() {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasksServiceData.clear()
    }


}