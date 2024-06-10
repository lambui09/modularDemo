package com.lambui.demomodular.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambui.demomodular.api.ResultApi
import com.lambui.demomodular.data.ScheduleItem
import com.lambui.demomodular.data.Workout
import com.lambui.demomodular.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _schedule = MutableStateFlow(emptyList<ScheduleItem>())

    val schedule = _schedule.asStateFlow()

    init {
        val schedules = workoutRepository.getCurrentWeekDates().map {
            ScheduleItem(it)
        }
        _schedule.value = schedules
        loadWorkout()
    }

    private fun loadWorkout() {
        viewModelScope.launch {
            updateScheduleWorkout(workouts = workoutRepository.getWorkoutsFromDatabase())
            workoutRepository.getWorkouts().onEach {
                when (it) {
                    is ResultApi.Error -> {

                    }

                    is ResultApi.Success -> {
                        updateScheduleWorkout(it.data)
                    }
                }
            }.singleOrNull()
        }
    }

    private fun updateScheduleWorkout(workouts: List<Workout>?) {
        if (workouts.isNullOrEmpty()) return
        val new = mutableListOf<ScheduleItem>()
        _schedule.value.forEach { scheduleItem ->
            val workout = workouts.firstOrNull { it.dayLocalDate == scheduleItem.localDate }
            new.add(scheduleItem.copy(workout = workout))
        }
        _schedule.value = new
    }
}