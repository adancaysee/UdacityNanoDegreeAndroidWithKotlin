package com.udacity.todo.data.source.remote

import com.udacity.todo.data.source.local.TaskEntity
import kotlinx.coroutines.delay

/**
 * *** NOTE ***
 * I used TaskEntity here too. Because my remote data source is fake. It does the same things as local data source.
 * That's why I used it here too
 */

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

class TasksRemoteDataSource {

    private var tasksServiceData = ArrayList<TaskEntity>()

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    suspend fun fetchTasks(): List<TaskEntity> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasksServiceData
    }

    suspend fun fetchTask(taskId: String): TaskEntity? {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasksServiceData.find {
            it.id == taskId
        }
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
        val index = tasksServiceData.withIndex().first { it.value.id == task.id }.index
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