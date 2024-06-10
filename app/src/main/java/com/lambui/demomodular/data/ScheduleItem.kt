package com.lambui.demomodular.data

import org.joda.time.LocalDate

data class ScheduleItem(val localDate: LocalDate, var workout: Workout? = null)