package com.example.todoappdatenbank.database.dataclass

import java.time.LocalDateTime

data class ToDoDataClass(
    val id: Int = 0,
    val name: String,
    val description: String,
    val priority: Int,
    val deadline: LocalDateTime,
    val state: Boolean
)