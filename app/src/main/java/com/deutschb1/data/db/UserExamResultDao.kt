package com.deutschb1.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deutschb1.data.db.entities.UserExamResult
import kotlinx.coroutines.flow.Flow

@Dao
interface UserExamResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: UserExamResult)

    @Query("SELECT * FROM user_exam_results WHERE examId = :examId")
    fun getResultsForExam(examId: String): Flow<List<UserExamResult>>

    @Query("SELECT * FROM user_exam_results")
    fun getAllResults(): Flow<List<UserExamResult>>
}
