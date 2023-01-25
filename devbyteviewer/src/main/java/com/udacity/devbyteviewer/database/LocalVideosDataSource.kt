package com.udacity.devbyteviewer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DevByteVideoEntity::class], version = 2, exportSchema = false)
abstract class DevByteDatabase : RoomDatabase() {

    abstract val dao: DevByteVideoDao

    companion object {
        @Volatile
        private var INSTANCE: DevByteDatabase? = null
        fun getInstance(context: Context): DevByteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        DevByteDatabase::class.java,
                        "dev_byte_database"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}

//OTHER METHOD IN UDACITY COURSE
@Volatile
private lateinit var INSTANCE: DevByteDatabase

fun getDatabase(context: Context): DevByteDatabase {
    synchronized(DevByteDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                DevByteDatabase::class.java,
                "dev_byte_database"
            ).build()
        }
    }
    return INSTANCE
}