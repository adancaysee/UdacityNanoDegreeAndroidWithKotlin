package com.udacity.devbyteviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dev_byte_playlist_table")
data class DevByteVideoEntity(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)