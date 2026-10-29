package com.example.practica06recyclerview

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val completed: Boolean
)
