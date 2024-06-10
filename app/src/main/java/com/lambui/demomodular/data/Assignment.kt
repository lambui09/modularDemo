package com.lambui.demomodular.data

import com.google.gson.annotations.SerializedName
import com.lambui.demomodular.utils.convertToLocalDate
import org.joda.time.LocalDate

data class Assignment(
    @SerializedName("_id")
    val id: String,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("client")
    val client: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("day")
    val day: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("exercises_completed")
    val exercisesCompleted: Int?,
    @SerializedName("exercises_count")
    val exercisesCount: Int?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("duration")
    val duration: Int?
) {
    val isCompleted: Boolean
        get() {
            return exercisesCompleted == 1
        }

    val isMissed: Boolean
        get() {
            return isCompleted.not() && day?.convertToLocalDate() != null && LocalDate.now() > day.convertToLocalDate()
        }

    val isFuture: Boolean
        get() {
            return day?.convertToLocalDate() != null && LocalDate.now() < day.convertToLocalDate()
        }
}