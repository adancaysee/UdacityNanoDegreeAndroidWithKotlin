package com.udacity.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDao {

    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * FROM daily_sleep_quality_table WHERE night_id = :key")
    fun get(key: Long): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY night_id DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY night_id LIMIT 1")
    fun getTonight():SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()


}