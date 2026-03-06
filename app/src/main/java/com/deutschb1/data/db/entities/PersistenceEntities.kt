package com.deutschb1.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_progress")
data class FlashcardProgress(
    @PrimaryKey val cardId: Int,
    val isMastered: Boolean = false,
    val nextReviewAt: Long = 0L,
    val lastScore: Int = 0
)

@Entity(tableName = "saved_words")
data class SavedWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val translation: String,
    val example: String = "",
    val savedAt: Long = System.currentTimeMillis()
)
