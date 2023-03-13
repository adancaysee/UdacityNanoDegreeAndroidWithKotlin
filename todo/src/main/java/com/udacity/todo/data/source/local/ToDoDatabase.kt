package com.udacity.todo.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val tasksDao: TasksDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: ToDoDatabase
        fun getInstance(context: Context): ToDoDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ToDoDatabase::class.java,
                        "todo-database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }

}