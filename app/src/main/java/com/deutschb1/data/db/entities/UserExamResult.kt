package com.deutschb1.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill

@Entity(tableName = "user_exam_results")
data class UserExamResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val examId: String,
    val provider: ExamProvider,
    val skill: ExamSkill,
    val score: Int,
    val totalQuestions: Int,
    val completedAt: Long
)
