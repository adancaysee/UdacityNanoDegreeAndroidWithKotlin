package com.udacity.todo.data.domain

import com.udacity.todo.data.source.local.TaskEntity

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean
) {
    val titleForList: String
        get() = title.ifEmpty { description }
}

fun Task.asDatabase() : TaskEntity = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    isCompleted = this.isCompleted
)
