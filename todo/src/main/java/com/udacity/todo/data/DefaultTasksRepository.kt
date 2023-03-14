package com.udacity.todo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.todo.data.source.local.TaskEntity
import com.udacity.todo.data.source.local.TasksDao
import com.udacity.todo.data.source.local.asDomain
import com.udacity.todo.data.source.remote.TasksRemoteDataSource
import com.udacity.todo.domain.Task
import kotlinx.coroutines.*

class DefaultTasksRepository(
    private val tasksLocalDataSource: TasksDao,
    private val tasksRemoteDataSource: TasksRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return Transformations.map(tasksLocalDataSource.observeTasks()) {
            Result.Success(it.asDomain())
        }
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        if (forceUpdate) {
            refreshTasks()
        }
        return withContext(dispatcher) {
            return@withContext try {
                Result.Success(tasksLocalDataSource.getTasks().asDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun refreshTasks() {
        withContext(dispatcher) {
            val result = tasksRemoteDataSource.fetchTasks()
            tasksLocalDataSource.deleteAllTasks()
            tasksLocalDataSource.insertTasks(result)
        }
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return Transformations.map(tasksLocalDataSource.observeTaskById(taskId)) {
            Result.Success(it.asDomain())
        }
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        if (forceUpdate) {
            refreshTask(taskId)
        }
        return withContext(dispatcher) {
            return@withContext try {
                val task = tasksLocalDataSource.getTaskById(taskId)
                if (task != null) {
                    Result.Success(task.asDomain())
                } else {
                    Result.Error(Exception("Task not found!"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun refreshTask(taskId: String) {
        withContext(dispatcher) {
            val remoteTask = tasksRemoteDataSource.fetchTask(taskId)
            remoteTask?.let {
                tasksLocalDataSource.updateTask(remoteTask)
            }

        }
    }

    override suspend fun saveTask(task: TaskEntity) {
        withContext(dispatcher) {
            coroutineScope {
                launch { tasksRemoteDataSource.saveTask(task) }
                launch { tasksLocalDataSource.insertTask(task) }
            }
        }
    }

    override suspend fun completeTask(task: TaskEntity) {
        withContext(dispatcher) {
            val newTask = task.copy(isCompleted = true)
            coroutineScope {
                launch { tasksRemoteDataSource.updateTask(newTask) }
                launch { tasksLocalDataSource.updateTask(newTask) }
            }
        }
    }

    override suspend fun activeTask(task: TaskEntity) {
        withContext(dispatcher) {
            val newTask = task.copy(isCompleted = false)
            coroutineScope {
                launch { tasksRemoteDataSource.updateTask(newTask) }
                launch { tasksLocalDataSource.updateTask(newTask) }
            }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        withContext(dispatcher) {
            coroutineScope {
                launch { tasksRemoteDataSource.deleteTask(taskId) }
                launch { tasksLocalDataSource.deleteTask(taskId) }
            }
        }
    }

    override suspend fun deleteCompletedTasks() {
        withContext(dispatcher) {
            launch { tasksRemoteDataSource.deleteCompletedTasks() }
            launch { tasksLocalDataSource.deleteCompletedTasks() }
        }
    }

    override suspend fun deleteTasks() {
        withContext(dispatcher) {
            launch { tasksRemoteDataSource.deleteAllTasks() }
            launch { tasksLocalDataSource.deleteAllTasks() }
        }
    }
}