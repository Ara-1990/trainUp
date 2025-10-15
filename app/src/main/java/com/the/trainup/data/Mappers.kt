package com.the.trainup.data

import com.the.trainup.data.remote.dto.ExerciseDto
import com.the.trainup.domain.Exercise
import com.the.trainup.domain.SetEntry



fun ExerciseDto.toEntity(existingId: Long? = null): ExerciseEntity =
    ExerciseEntity(
        id = existingId ?: 0L,
        name = name.trim(),
        muscleGroup = (muscle ?: "unknown").trim(),
        type = type,
        equipment = equipment,
        difficulty = difficulty,
        instructions = instructions
    )
    fun ExerciseEntity.toDomain() = Exercise(id, name, muscleGroup, type, equipment, difficulty, instructions)


    fun Exercise.toEntity() = ExerciseEntity(id, name, muscleGroup, type, equipment, difficulty, instructions)


    fun SetEntity.toDomain() = SetEntry(id, workoutId, exerciseId, reps, weightKg)

