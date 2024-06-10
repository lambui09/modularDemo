package com.lambui.demomodular.repository

import com.lambui.demomodular.api.ApiService
import com.lambui.demomodular.api.ResultApi
import com.lambui.demomodular.data.Workout
import com.lambui.demomodular.data.database.AppDatabase
import com.lambui.demomodular.utils.handleApiResponse
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
) {

    suspend fun getWorkouts(): Flow<ResultApi<List<Workout>>> {
        return handleApiResponse {
            apiService.getWorkouts().data.also { workouts ->
                if (workouts.isNullOrEmpty().not()) {
                    appDatabase.appDao().insertWorkouts(workouts!!)
                }
            } ?: emptyList()
        }
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val now = LocalDate.now()
        val monday = now.withDayOfWeek(DateTimeConstants.MONDAY)
        return (0..6).map { day ->
            monday.plusDays(day)
        }
    }

    suspend fun getWorkoutsFromDatabase(): List<Workout> {
        return appDatabase.appDao().getWorkouts()
    }
}