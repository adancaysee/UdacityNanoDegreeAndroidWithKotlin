package com.udacity.todo.data

import androidx.lifecycle.LiveData
import com.udacity.todo.data.source.local.TaskEntity
import com.udacity.todo.domain.Task

interface TasksRepository {

    fun observeTasks(): Result<LiveData<List<Task>>>

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>

    suspend fun refreshTasks()

    fun observeTask(taskId: String): Result<LiveData<Task>>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: TaskEntity)

    suspend fun completeTask(task: TaskEntity)

    suspend fun activeTask(taskEntity: TaskEntity)

    suspend fun deleteTask(taskId: String)

    suspend fun deleteCompletedTasks()

    suspend fun deleteTasks()


}