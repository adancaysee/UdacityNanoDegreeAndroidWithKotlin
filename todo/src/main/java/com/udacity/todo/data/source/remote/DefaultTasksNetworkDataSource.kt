package com.udacity.todo.data.source.remote

import com.udacity.todo.data.source.local.TaskEntity
import kotlinx.coroutines.delay

/**
 * *** NOTE ***
 * I used TaskEntity here too. Because my remote data source is fake. It does the same things as local data source.
 * That's why I used it here too
 */

private const val SERVICE_LATENCY_IN_MILLIS = 2000L

class DefaultTasksNetworkDataSource : TasksNetworkDataSource {

    private var tasksServiceData = ArrayList<TaskEntity>()

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    override suspend fun fetchTasks(): List<TaskEntity> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasksServiceData
    }

    override suspend fun fetchTask(taskId: String): TaskEntity? {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasksServiceData.find {
            it.id == taskId
        }
    }

    private fun addTask(title: String, description: String) {
        val newTask = TaskEntity(title = title, description = description)
        tasksServiceData.add(newTask)
    }

    override suspend fun saveTask(task: TaskEntity) {
        delay(0)
        tasksServiceData.add(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        delay(0)
        val index = tasksServiceData.withIndex().first { it.value.id == task.id }.index
        if (index != -1) {
            tasksServiceData[index] = task
        }
    }

    override suspend fun deleteTask(taskId: String) {
        delay(0)
        val task = tasksServiceData.find {
            it.id == taskId
        }
        task?.let {
            tasksServiceData.remove(task)
        }
    }

    override suspend fun deleteCompletedTasks() {
        delay(0)
        tasksServiceData.removeAll { it.isCompleted }
    }

    override suspend fun deleteAllTasks() {
        delay(0)
        tasksServiceData.clear()
    }


}