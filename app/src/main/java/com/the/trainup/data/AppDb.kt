package com.the.trainup.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ExerciseEntity::class, WorkoutEntity::class, SetEntity::class],
    version = 1, exportSchema = true
)
abstract class AppDb : RoomDatabase() {
   abstract fun exerciseDao(): ExerciseDao
   abstract fun setDao(): SetDao
}