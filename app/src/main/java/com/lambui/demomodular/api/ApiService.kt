package com.lambui.demomodular.api

import retrofit2.http.GET

interface ApiService {

    @GET("workouts")
    suspend fun getWorkouts(): WorkoutResponse
}