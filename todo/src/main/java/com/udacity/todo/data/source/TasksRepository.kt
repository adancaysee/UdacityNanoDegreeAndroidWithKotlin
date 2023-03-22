package com.udacity.todo.data.source

import androidx.lifecycle.LiveData
import com.udacity.todo.data.Result
import com.udacity.todo.data.domain.Task

interface TasksRepository {

    fun observeTasks(filterType: TasksFilterType): LiveData<List<Task>?>

    suspend fun getTasks(forceUpdate: Boolean = false): List<Task>?

    suspend fun refreshTasks()

    fun observeTask(taskId: String): LiveData<Task?>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Task?

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun activeTask(task: Task)

    suspend fun deleteTask(taskId: String)

    suspend fun deleteCompletedTasks(): Result<Int>

    suspend fun deleteTasks()
}