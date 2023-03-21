package com.udacity.todo.data.source.remote

import com.udacity.todo.data.source.local.TaskEntity

interface TasksNetworkDataSource {

    suspend fun fetchTasks(): List<TaskEntity>

    suspend fun fetchTask(taskId: String): TaskEntity?

    suspend fun saveTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)

    suspend fun deleteTask(taskId: String)

    suspend fun deleteCompletedTasks()

    suspend fun deleteAllTasks()

}