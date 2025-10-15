package com.the.trainup.domain

interface ExerciseRepository {
    suspend fun getAll(): List<Exercise>
    suspend fun insert(exercise: Exercise)
}



interface SetRepository {
    suspend fun addSet(entry: SetEntry)
    suspend fun getSetsForWorkout(workoutId: Long): List<SetEntry>
    suspend fun getSetsByExercise(exerciseId: Long): List<SetEntry>
}
