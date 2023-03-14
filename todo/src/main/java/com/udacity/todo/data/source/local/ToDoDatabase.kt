package com.udacity.todo.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

}