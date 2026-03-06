package com.deutschb1.data.db

import androidx.room.*
import com.deutschb1.data.db.entities.FlashcardProgress
import com.deutschb1.data.db.entities.SavedWord
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProgress(progress: FlashcardProgress)

    @Query("SELECT * FROM flashcard_progress WHERE cardId = :cardId")
    suspend fun getProgressForCard(cardId: Int): FlashcardProgress?

    @Query("SELECT * FROM flashcard_progress")
    fun getAllProgress(): Flow<List<FlashcardProgress>>
}

@Dao
interface SavedWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWord(word: SavedWord)

    @Delete
    suspend fun deleteWord(word: SavedWord)

    @Query("SELECT * FROM saved_words ORDER BY savedAt DESC")
    fun getAllSavedWords(): Flow<List<SavedWord>>
}
