package com.udacity.todo.domain

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)
