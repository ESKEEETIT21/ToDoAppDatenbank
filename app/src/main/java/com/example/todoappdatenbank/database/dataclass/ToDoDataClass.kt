package com.example.todoappdatenbank.database.dataclass

import java.time.LocalDateTime

/**
 * Data class representing a Todo item with properties for id, name, description, priority, deadline, and state.
 *
 * @param id The unique identifier of the todo.
 * @param name The name/title of the todo.
 * @param description The detailed description of the todo.
 * @param priority The priority level of the todo (integer representation).
 * @param deadline The deadline for the todo.
 * @param state The completion state of the todo (true for completed, false for active).
 */
data class ToDoDataClass(
    val id: Int = 0,
    val name: String,
    val description: String,
    val priority: Int,
    val deadline: LocalDateTime,
    val state: Boolean
)