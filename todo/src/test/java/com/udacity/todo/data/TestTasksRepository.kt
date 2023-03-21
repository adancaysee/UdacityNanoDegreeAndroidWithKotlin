package com.udacity.todo.data

import androidx.lifecycle.LiveData
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.source.TasksFilterType
import com.udacity.todo.data.source.TasksRepository

class TestTasksRepository : TasksRepository {
    override fun observeTasks(filterType: TasksFilterType): LiveData<List<Task>?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTasks() {

    }

    override fun observeTask(taskId: String): LiveData<Task?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompletedTasks(): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTasks() {
        TODO("Not yet implemented")
    }
}