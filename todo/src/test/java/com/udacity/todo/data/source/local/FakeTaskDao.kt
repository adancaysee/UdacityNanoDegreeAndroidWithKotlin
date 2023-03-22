package com.udacity.todo.data.source.local

import androidx.lifecycle.LiveData

class FakeTaskDao(
    var fakeTasks: MutableList<TaskEntity>? = mutableListOf()
) : TasksDao {

    override fun observeTasks(): LiveData<List<TaskEntity>?> {
        throw NotImplementedError("Unused in tests")
    }

    override fun observeFilteringTasks(isCompleted: Boolean): LiveData<List<TaskEntity>?> {
        throw NotImplementedError("Unused in tests")
    }

    override fun getFilteringTasks(isCompleted: Boolean): List<TaskEntity>? {
        throw NotImplementedError("Unused in tests")
    }

    override fun getTasks(): List<TaskEntity> {
        return fakeTasks ?: listOf()
    }

    override fun observeTaskById(taskId: String): LiveData<TaskEntity?> {
        throw NotImplementedError("Unused in tests")
    }

    override fun getTaskById(taskId: String): TaskEntity? {
        throw NotImplementedError("Unused in tests")
    }

    override fun insertTask(task: TaskEntity) {
        throw NotImplementedError("Unused in tests")
    }

    override fun insertTasks(tasks: List<TaskEntity>) {
        this.fakeTasks?.addAll(tasks)
    }

    override fun updateTask(task: TaskEntity) {
        throw NotImplementedError("Unused in tests")
    }

    override fun deleteTask(taskId: String) {
        throw NotImplementedError("Unused in tests")
    }

    override fun deleteTasks(task: List<TaskEntity>) {
        throw NotImplementedError("Unused in tests")
    }

    override fun deleteAllTasks() {
        fakeTasks?.clear()
    }
}