package com.the.trainup.domain


data class Exercise(
    val id: Long,
    val name: String,
    val muscleGroup: String,
    val type: String? = null,
    val equipment: String? = null,
    val difficulty: String? = null,
    val instructions: String? = null

)



data class SetEntry(
    val id: Long,
    val workoutId: Long,
    val exerciseId: Long,
    val reps: Int,
    val weightKg: Float
)

data class ExercisesUiState(
    val isLoading: Boolean = false,
    val items: List<Exercise> = emptyList(),
    val query: String = "",
    val muscle: String? = null,
    val type: String? = null,
    val difficulty: String? = null,
    val error: String? = null
)