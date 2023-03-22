package com.udacity.todo.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    fun observeTasks(): LiveData<List<TaskEntity>?>

    @Query("SELECT * FROM tasks_table WHERE isCompleted = :isCompleted")
    fun observeFilteringTasks(isCompleted: Boolean): LiveData<List<TaskEntity>?>

    @Query("SELECT * FROM tasks_table WHERE isCompleted = :isCompleted")
    fun getFilteringTasks(isCompleted: Boolean): List<TaskEntity>?

    @Query("SELECT * FROM tasks_table")
    fun getTasks(): List<TaskEntity>?

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    fun observeTaskById(taskId: String): LiveData<TaskEntity?>

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(tasks: List<TaskEntity>)

    @Update
    fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks_table WHERE id = :taskId")
    fun deleteTask(taskId: String)

    @Delete
    fun deleteTasks(task: List<TaskEntity>)

    @Query("DELETE FROM tasks_table")
    fun deleteAllTasks()
}