package com.udacity.todo.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    fun observeTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks_table")
    suspend fun getTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    fun observeTask(taskId: String): LiveData<TaskEntity>

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    suspend fun getTask(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM tasks_table WHERE isCompleted = 1")
    suspend fun deleteCompletedTasks()

    @Query("DELETE FROM tasks_table")
    suspend fun deleteTasks()
}