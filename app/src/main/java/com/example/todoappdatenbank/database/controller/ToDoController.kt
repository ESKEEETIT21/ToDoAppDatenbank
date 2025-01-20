package com.example.todoappdatenbank.database.controller

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todoappdatenbank.database.DbHelper
import com.example.todoappdatenbank.database.dataclass.ToDoDataClass
import com.example.todoappdatenbank.database.dataclass.PrioritiesDataClass
import java.time.LocalDateTime

class ToDoController(context: Context) {
    private val dbHelper = DbHelper(context)

    /**
     * Inserts a new todo item into the database.
     *
     * @param todo The todo item to be inserted.
     * @return True if the insert operation was successful, false otherwise.
     */
    fun insertTodo(todo: ToDoDataClass): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", todo.name)
                put("description", todo.description)
                put("priority", todo.priority)
                put("enddate", todo.deadline.toString())
                put("state", todo.state)
            }
            val result = db.insert("tasks", null, values)
            result != -1L
        } catch (e: Exception) {
            android.util.Log.e("ToDoController", "Insert failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Updates an existing todo item in the database.
     *
     * @param todo The todo item to be updated.
     * @return True if the update operation was successful, false otherwise.
     */
    fun updateTodo(todo: ToDoDataClass): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", todo.name)
                put("description", todo.description)
                put("priority", todo.priority)
                put("enddate", todo.deadline.toString())
                put("state", todo.state )
            }
            val result = db.update("tasks", values, "id = ?", arrayOf(todo.id.toString()))
            Log.d("ToDoController", "Update result: $result, ToDo ID: ${todo.id}")
            result > 0
        } catch (e: Exception) {
            Log.e("ToDoController", "Update failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Deletes a todo item from the database.
     *
     * @param todoId The ID of the todo item to be deleted.
     * @return True if the delete operation was successful, false otherwise.
     */
    fun deleteTodo(todoId: Int): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val result = db.delete("tasks", "id = ?", arrayOf(todoId.toString()))
            result > 0
        } catch (e: Exception) {
            Log.e("ToDoController", "Delete failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Retrieves all todo items from the database.
     *
     * @return A list of all todo items.
     */
    fun getAllTodos(): List<ToDoDataClass> {
        val db = dbHelper.readableDatabase
        val todos = mutableListOf<ToDoDataClass>()
        val cursor = db.rawQuery("SELECT * FROM tasks", null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val todo = ToDoDataClass(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority")),
                        deadline = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("enddate"))),
                        state = cursor.getInt(cursor.getColumnIndexOrThrow("state")) > 0
                    )
                    todos.add(todo)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("ToDoController", "Fetching todos failed", e)
        } finally {
            cursor.close()
            db.close()
        }
        return todos
    }

    /**
     * Retrieves all priority levels from the database.
     *
     * @return A list of all priority levels.
     */
    fun getAllPriorities(): List<PrioritiesDataClass> {
        val db = dbHelper.readableDatabase
        val priorities = mutableListOf<PrioritiesDataClass>()
        val cursor = db.rawQuery("SELECT * FROM priorities", null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val levels = PrioritiesDataClass(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        level = cursor.getString(cursor.getColumnIndexOrThrow("level")),
                    )
                    priorities.add(levels)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("ToDoController", "Fetching todos failed", e)
        } finally {
            cursor.close()
            db.close()
        }
        return priorities
    }
}