package com.example.todoappdatenbank.database.dataclass

/**
 * Data class representing a priority level for a todo item.
 *
 * @param id The unique identifier of the priority level.
 * @param level The name/label of the priority level (e.g., "High", "Medium", "Low").
 */
data class PrioritiesDataClass(
    val id: Int = 0,
    val level: String
)