package com.deutschb1.data.db

import android.content.Context

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val db = AppDatabase.getInstance(context)
            database = db
            db
        }
    }

    fun getResultDao(context: Context) = getDatabase(context).userExamResultDao()
    fun getFlashcardDao(context: Context) = getDatabase(context).flashcardDao()
    fun getSavedWordDao(context: Context) = getDatabase(context).savedWordDao()
}
