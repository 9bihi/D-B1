package com.deutschb1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deutschb1.data.db.entities.UserExamResult
import com.deutschb1.data.db.entities.FlashcardProgress
import com.deutschb1.data.db.entities.SavedWord

@Database(entities = [UserExamResult::class, FlashcardProgress::class, SavedWord::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userExamResultDao(): UserExamResultDao
    abstract fun flashcardDao(): FlashcardDao
    abstract fun savedWordDao(): SavedWordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "deutschb1_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
