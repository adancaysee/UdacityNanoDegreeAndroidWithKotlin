package com.udacity.todo.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.todo.data.domain.Task
import java.util.UUID

/**
 * Data layer representation of [Task]
 */

@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean = false
)

fun TaskEntity.asDomain() =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )

fun List<TaskEntity>.asDomain() = map {
    Task(
        id = it.id,
        title = it.title,
        description = it.description,
        isCompleted = it.isCompleted
    )
}
