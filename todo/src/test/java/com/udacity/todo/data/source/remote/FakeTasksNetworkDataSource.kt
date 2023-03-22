package com.udacity.todo.data.source.remote

import com.udacity.todo.data.source.local.TaskEntity

class FakeTasksNetworkDataSource(
    var tasks: MutableList<TaskEntity> = mutableListOf()
) : TasksNetworkDataSource {

    override suspend fun fetchTasks(): List<TaskEntity> {
        return tasks
    }

    override suspend fun fetchTask(taskId: String): TaskEntity? {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun saveTask(task: TaskEntity) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateTask(task: TaskEntity) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteCompletedTasks() {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun deleteAllTasks() {
        throw NotImplementedError("Unused in tests")
    }
}