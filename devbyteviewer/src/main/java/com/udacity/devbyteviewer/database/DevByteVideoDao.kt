package com.udacity.devbyteviewer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DevByteVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayList(list: List<DevByteVideoEntity>)

    @Query("SELECT * FROM dev_byte_playlist_table")
    suspend fun getPlayList(): List<DevByteVideoEntity>
}