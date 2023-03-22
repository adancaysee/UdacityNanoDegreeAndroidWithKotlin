package com.udacity.todo

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.udacity.todo.data.source.DefaultTasksRepository
import com.udacity.todo.data.source.TasksRepository
import com.udacity.todo.data.source.local.TasksDao
import com.udacity.todo.data.source.local.ToDoDatabase
import com.udacity.todo.data.source.remote.DefaultTasksNetworkDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private var database: ToDoDatabase? = null

    @Volatile
    var tasksRepository: TasksRepository? = null
        @VisibleForTesting set

    fun providesTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTaskRepository(context)
        }
    }

    private fun createTaskRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(
            createTasksLocalDataSource(context),
            DefaultTasksNetworkDataSource()
        )
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTasksLocalDataSource(context: Context): TasksDao {
        val database = database ?: createDatabase(context)
        return database.tasksDao
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        val roomDb = Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo-database"
        ).build()
        database = roomDb
        return roomDb
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(this) {
            runBlocking {
                tasksRepository?.deleteTasks()
            }

            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            tasksRepository = null
        }
    }

}