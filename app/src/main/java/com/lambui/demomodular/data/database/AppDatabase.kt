package com.lambui.demomodular.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lambui.demomodular.data.Workout
import com.lambui.demomodular.data.database.AppConverter
import com.lambui.demomodular.data.database.AppDao

@Database(
    entities = [Workout::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}