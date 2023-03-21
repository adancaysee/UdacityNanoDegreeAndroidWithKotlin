package com.udacity.todo.data.source.local

import androidx.lifecycle.LiveData

class TestTaskDao : TasksDao {
    override fun observeTasks(): LiveData<List<TaskEntity>?> {
        TODO("Not yet implemented")
    }

    override fun observeFilteringTasks(isCompleted: Boolean): LiveData<List<TaskEntity>?> {
        TODO("Not yet implemented")
    }

    override fun getFilteringTasks(isCompleted: Boolean): List<TaskEntity>? {
        TODO("Not yet implemented")
    }

    override fun getTasks(): List<TaskEntity> {
        TODO("Not yet implemented")
    }

    override fun observeTaskById(taskId: String): LiveData<TaskEntity?> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(taskId: String): TaskEntity? {
        TODO("Not yet implemented")
    }

    override fun insertTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override fun insertTasks(tasks: List<TaskEntity>) {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteTasks(task: List<TaskEntity>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllTasks() {
        TODO("Not yet implemented")
    }
}