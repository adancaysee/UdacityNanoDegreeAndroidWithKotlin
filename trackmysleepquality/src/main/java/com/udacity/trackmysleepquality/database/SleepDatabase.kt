package com.udacity.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Volatile --> Variable is always up to date and the same for all execution threads.(volatile variable will ne never cached)
 * The value of a volatile variable will never be cached, and all writes and
 * reads will be done to and from the main memory. It means that changes made by one
 * thread to shared data are visible to other threads.
 *
 * synchronized --> Only one thread can enter to the this block in the same time
 */

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    abstract val sleepDao: SleepDao

    companion object {

        @Volatile
        private var INSTANCE: SleepDatabase? = null

        fun getInstance(context: Context): SleepDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        SleepDatabase::class.java,
                        "sleep_history_database3"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }


}