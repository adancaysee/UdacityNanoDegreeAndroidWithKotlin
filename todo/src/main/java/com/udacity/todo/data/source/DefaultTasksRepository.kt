package com.udacity.todo.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.todo.data.Result
import com.udacity.todo.data.source.local.TasksDao
import com.udacity.todo.data.source.local.asDomain
import com.udacity.todo.data.domain.Task
import com.udacity.todo.data.domain.asDatabase
import com.udacity.todo.data.source.remote.TasksNetworkDataSource
import com.udacity.todo.util.wrapEspressoIdlingResource
import kotlinx.coroutines.*

enum class TasksFilterType { ALL_TASKS, ACTIVE_TASKS, COMPLETED_TASKS }

class DefaultTasksRepository(
    private val tasksDao: TasksDao,
    private val tasksNetworkDataSource: TasksNetworkDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    override fun observeTasks(filterType: TasksFilterType): LiveData<List<Task>?> =
        wrapEspressoIdlingResource {
            return when (filterType) {
                TasksFilterType.ALL_TASKS -> Transformations.map(tasksDao.observeTasks()) {
                    it?.asDomain()
                }
                else -> {
                    Transformations.map(
                        tasksDao.observeFilteringTasks(filterType != TasksFilterType.ACTIVE_TASKS)
                    ) {
                        it?.asDomain()
                    }
                }
            }
        }

    override suspend fun getTasks(forceUpdate: Boolean): List<Task>? = wrapEspressoIdlingResource {
        if (forceUpdate) {
            refreshTasks()
        }
        return withContext(dispatcher) {
            return@withContext try {
                tasksDao.getTasks()!!.asDomain()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun refreshTasks() = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            val result = tasksNetworkDataSource.fetchTasks()
            tasksDao.deleteAllTasks()
            tasksDao.insertTasks(result)
        }
    }

    override fun observeTask(taskId: String): LiveData<Task?> = wrapEspressoIdlingResource {
        return Transformations.map(tasksDao.observeTaskById(taskId)) {
            it?.asDomain()
        }
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Task? =
        wrapEspressoIdlingResource {
            if (forceUpdate) {
                refreshTask(taskId)
            }
            return withContext(dispatcher) {
                return@withContext try {
                    tasksDao.getTaskById(taskId)?.asDomain()
                } catch (e: Exception) {
                    null
                }
            }
        }

    override suspend fun refreshTask(taskId: String): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            val remoteTask = tasksNetworkDataSource.fetchTask(taskId)
            remoteTask?.let {
                tasksDao.updateTask(remoteTask)
            }
        }
    }

    override suspend fun saveTask(task: Task): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            coroutineScope {
                launch { tasksNetworkDataSource.saveTask(taskEntity) }
                launch { tasksDao.insertTask(taskEntity) }
            }
        }
    }

    override suspend fun completeTask(task: Task): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            taskEntity.isCompleted = true
            coroutineScope {
                launch { tasksNetworkDataSource.updateTask(taskEntity) }
                launch { tasksDao.updateTask(taskEntity) }
            }
        }
    }

    override suspend fun activeTask(task: Task): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            val taskEntity = task.asDatabase()
            taskEntity.isCompleted = false
            coroutineScope {
                launch { tasksNetworkDataSource.updateTask(taskEntity) }
                launch { tasksDao.updateTask(taskEntity) }
            }
        }
    }

    override suspend fun deleteTask(taskId: String): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            tasksNetworkDataSource.deleteTask(taskId)
            tasksDao.deleteTask(taskId)
        }
    }

    override suspend fun deleteCompletedTasks(): Result<Int> = wrapEspressoIdlingResource {
        return withContext(dispatcher) {
            val completedTasks = tasksDao.getFilteringTasks(true)
            if (completedTasks.isNullOrEmpty())
                return@withContext Result.Error(Exception("Not found completed tasks"))
            launch { tasksNetworkDataSource.deleteCompletedTasks() }
            launch { tasksDao.deleteTasks(completedTasks) }
            return@withContext Result.Success(completedTasks.size)
        }
    }

    override suspend fun deleteTasks(): Unit = wrapEspressoIdlingResource {
        withContext(dispatcher) {
            launch { tasksNetworkDataSource.deleteAllTasks() }
            launch { tasksDao.deleteAllTasks() }
        }
    }
}