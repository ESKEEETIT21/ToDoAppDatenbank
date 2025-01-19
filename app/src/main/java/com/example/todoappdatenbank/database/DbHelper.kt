package com.example.todoappdatenbank.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

class DbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Die Methode bleibt leer, da wir eine bestehende Datenbank aus den assets verwende.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        context.deleteDatabase(DATABASE_NAME)
        copyDatabaseFromAssets()
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getWritableDatabase()
    }

    private fun copyDatabaseFromAssets() {
        val dbPath = context.getDatabasePath(DATABASE_NAME)
        if (!dbPath.exists()) {
            try {
                context.assets.open(DATABASE_NAME).use { inputStream ->
                    FileOutputStream(dbPath).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                android.util.Log.d("DbHelper", "Database copied successfully to: ${dbPath.absolutePath}")
                android.util.Log.d("DbHelper", "Database size: ${dbPath.length()} bytes")

            } catch (e: Exception) {
                android.util.Log.e("DbHelper", "Error copying database", e)
            }
        }
    }

    companion object {
        const val DATABASE_NAME = "DatenbankToDo.db"
        const val DATABASE_VERSION = 1
    }
}