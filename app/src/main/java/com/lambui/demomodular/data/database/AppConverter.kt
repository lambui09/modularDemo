package com.lambui.demomodular.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lambui.demomodular.data.Assignment
import javax.inject.Inject

@ProvidedTypeConverter
class AppConverter @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun fromWorkoutAssignments(assignments: List<Assignment>?): String? {
        return if (assignments == null) null else gson.toJson(assignments)
    }

    @TypeConverter
    fun toWorkoutAssignments(value: String?): List<Assignment>? {
        return if (value == null) null else gson.fromJson(
            value,
            object : TypeToken<List<Assignment>>() {}.type
        )
    }
}