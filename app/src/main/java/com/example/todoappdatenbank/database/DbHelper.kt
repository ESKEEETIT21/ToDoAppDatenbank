package com.example.todoappdatenbank.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

class DbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * onCreate method of SQLiteOpenHelper. This method is empty because the app uses an existing database from assets.
     *
     * @param db The SQLiteDatabase instance that is created when the database is accessed.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        // Die Methode bleibt leer, da wir eine bestehende Datenbank aus den assets verwende.
    }

    /**
     * onUpgrade method of SQLiteOpenHelper. It deletes the existing database and copies a new version from assets.
     *
     * @param db The SQLiteDatabase instance that is being upgraded.
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        context.deleteDatabase(DATABASE_NAME)
        copyDatabaseFromAssets()
    }

    /**
     * getReadableDatabase method of SQLiteOpenHelper. It ensures the database is copied from assets before returning
     * a readable database instance.
     *
     * @return A readable SQLiteDatabase instance.
     */
    override fun getReadableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getReadableDatabase()
    }

    /**
     * getWritableDatabase method of SQLiteOpenHelper. It ensures the database is copied from assets before returning
     * a writable database instance.
     *
     * @return A writable SQLiteDatabase instance.
     */
    override fun getWritableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getWritableDatabase()
    }

    /**
     * Copies the database from the assets folder to the application's database directory if it doesn't already exist.
     * Logs success or failure of the copy operation.
     */
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

    /**
     * Companion object containing constants for the database name and version.
     */
    companion object {
        const val DATABASE_NAME = "DatenbankToDo.db"
        const val DATABASE_VERSION = 1
    }
}