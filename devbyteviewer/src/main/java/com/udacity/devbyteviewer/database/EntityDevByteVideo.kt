package com.udacity.devbyteviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.devbyteviewer.domain.DevByteVideo

@Entity(tableName = "dev_byte_playlist_table")
data class EntityDevByteVideo(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)

fun List<EntityDevByteVideo>.asDomainModel(): List<DevByteVideo> {
    return map {
        DevByteVideo(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }
}