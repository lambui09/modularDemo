package com.lambui.demomodular.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.lambui.demomodular.data.Assignment
import com.lambui.demomodular.utils.convertToLocalDate
import org.joda.time.LocalDate

@Entity(tableName = "workouts", primaryKeys = ["workout_id"])
data class Workout(
    @SerializedName("_id")
    @ColumnInfo(name = "workout_id")
    val id: String,
    @SerializedName("assignments")
    @ColumnInfo(name = "workout_assignments")
    val assignments: List<Assignment>?,
    @SerializedName("trainer")
    @ColumnInfo(name = "workout_trainer")
    val trainer: String?,
    @SerializedName("client")
    @ColumnInfo(name = "workout_client")
    val client: String?,
    @SerializedName("day")
    @ColumnInfo(name = "workout_day")
    val day: String?,
    @SerializedName("date")
    @ColumnInfo(name = "workout_date")
    val date: String?
) {
    val dayLocalDate: LocalDate?
        get() {
            return day?.convertToLocalDate()
        }
}