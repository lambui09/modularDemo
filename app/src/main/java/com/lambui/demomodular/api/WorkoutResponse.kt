package com.lambui.demomodular.api

import com.google.gson.annotations.SerializedName
import com.lambui.demomodular.data.Workout

data class WorkoutResponse(
    @SerializedName("day_data")
    val data: List<Workout>?,
)