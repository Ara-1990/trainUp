package com.the.trainup.data

import com.the.trainup.domain.Exercise
import com.the.trainup.domain.ExerciseRepository
import com.the.trainup.domain.SetEntry
import com.the.trainup.domain.SetRepository


class ExerciseRepositoryImpl(
    private val dao: ExerciseDao,
) : ExerciseRepository {
    override suspend fun getAll() = dao.getAll().map { it.toDomain() }
    override suspend fun insert(exercise: Exercise) = dao.insert(exercise.toEntity())
}

class SetRepositoryImpl(
    private val dao: SetDao,
) : SetRepository {
    override suspend fun addSet(entry: SetEntry) = dao.insert(
        SetEntity(
            workoutId = entry.workoutId,
            exerciseId = entry.exerciseId,
            reps = entry.reps,
            weightKg = entry.weightKg
        )
    )

    override suspend fun getSetsForWorkout(workoutId: Long) =
        dao.getByWorkout(workoutId).map { it.toDomain() }

    override suspend fun getSetsByExercise(exerciseId: Long): List<SetEntry> =
        dao.getByExercise(exerciseId).map { it.toDomain() }
}
