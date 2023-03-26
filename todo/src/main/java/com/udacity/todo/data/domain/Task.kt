package com.udacity.todo.data.domain

import com.udacity.todo.data.source.local.TaskEntity
import java.util.*

data class Task(
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
) {
    val titleForList: String
        get() = title.ifEmpty { description }
}

fun Task.asDatabase(): TaskEntity = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isCompleted = this.isCompleted
)
