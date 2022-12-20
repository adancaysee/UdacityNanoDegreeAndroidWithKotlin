package com.udacity.trackmysleepquality.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * inMemoryDatabaseBuilder --> Create the database in memory
 * allowMainThreadQueries() --> Allowing main thread queries
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var dao: SleepDao
    private lateinit var db: SleepDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.sleepDao
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNight()
        runBlocking {
            dao.insert(night)
            val tonight = dao.getTonight()
            assertEquals(night,tonight)
        }
    }

}