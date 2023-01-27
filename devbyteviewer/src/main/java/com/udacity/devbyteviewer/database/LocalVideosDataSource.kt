package com.udacity.devbyteviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalVideosDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayList(list: List<EntityDevByteVideo>)

    @Query("SELECT * FROM dev_byte_playlist_table")
    fun getPlayList(): LiveData<List<EntityDevByteVideo>>
}