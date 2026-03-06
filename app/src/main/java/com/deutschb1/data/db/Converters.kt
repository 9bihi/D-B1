package com.deutschb1.data.db

import androidx.room.TypeConverter
import com.deutschb1.data.ExamProvider
import com.deutschb1.data.ExamSkill

class Converters {
    @TypeConverter
    fun fromProvider(value: ExamProvider): String = value.name

    @TypeConverter
    fun toProvider(value: String): ExamProvider = ExamProvider.valueOf(value)

    @TypeConverter
    fun fromSkill(value: ExamSkill): String = value.name

    @TypeConverter
    fun toSkill(value: String): ExamSkill = ExamSkill.valueOf(value)
}
