package com.udacity.todo.data.source.remote

import com.udacity.todo.data.source.local.TaskEntity

class TestTasksNetworkDataSource : TasksNetworkDataSource {
    override suspend fun fetchTasks(): List<TaskEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchTask(taskId: String): TaskEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }
}