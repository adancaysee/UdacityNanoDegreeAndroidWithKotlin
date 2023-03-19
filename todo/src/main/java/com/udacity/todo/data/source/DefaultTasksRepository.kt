package com.udacity.todo.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.todo.data.Result
import com.udacity.todo.data.source.local.TasksDao
import com.udacity.todo.data.source.local.asDomain
import com.udacity.todo.data.source.remote.TasksRemoteDataSource
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.domain.asDatabase
import kotlinx.coroutines.*

enum class TasksFilterType { ALL_TASKS, ACTIVE_TASKS, COMPLETED_TASKS }

class DefaultTasksRepository(
    private val tasksLocalDataSource: TasksDao,
    private val tasksRemoteDataSource: TasksRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    override fun observeTasks(filterType: TasksFilterType): LiveData<List<Task>?> {
        return when (filterType) {
            TasksFilterType.ALL_TASKS -> Transformations.map(tasksLocalDataSource.observeTasks()) {
                it?.asDomain()
            }
            else -> {
                Transformations.map(
                    tasksLocalDataSource.observeFilteringTasks(filterType != TasksFilterType.ACTIVE_TASKS)
                ) {
                    it?.asDomain()
                }
            }
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

    override fun observeTask(taskId: String): LiveData<Task?> {
        return Transformations.map(tasksLocalDataSource.observeTaskById(taskId)) {
            it?.asDomain()
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

    override suspend fun saveTask(task: Task) {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            coroutineScope {
                launch { tasksRemoteDataSource.saveTask(taskEntity) }
                launch { tasksLocalDataSource.insertTask(taskEntity) }
            }
        }
    }

    override suspend fun completeTask(task: Task) {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            taskEntity.isCompleted = true
            coroutineScope {
                launch { tasksRemoteDataSource.updateTask(taskEntity) }
                launch { tasksLocalDataSource.updateTask(taskEntity) }
            }
        }
    }

    override suspend fun activeTask(task: Task) {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            taskEntity.isCompleted = false
            coroutineScope {
                launch { tasksRemoteDataSource.updateTask(taskEntity) }
                launch { tasksLocalDataSource.updateTask(taskEntity) }
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

    override suspend fun deleteCompletedTasks(): Result<Int> {
        return withContext(dispatcher) {
            val completedTasks = tasksLocalDataSource.getFilteringTasks(true)
            if (completedTasks.isNullOrEmpty())
                return@withContext Result.Error(Exception("Not found completed tasks"))
            launch { tasksRemoteDataSource.deleteCompletedTasks() }
            launch { tasksLocalDataSource.deleteTasks(completedTasks) }
            return@withContext Result.Success(completedTasks.size)
        }
    }

    override suspend fun deleteTasks() {
        withContext(dispatcher) {
            launch { tasksRemoteDataSource.deleteAllTasks() }
            launch { tasksLocalDataSource.deleteAllTasks() }
        }
    }
}