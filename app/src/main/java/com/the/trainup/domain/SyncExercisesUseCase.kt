package com.the.trainup.domain

import com.the.trainup.data.remote.ExerciseRemoteDataSource
import javax.inject.Inject

class SyncExercisesUseCase @Inject constructor(
    private val remote: ExerciseRemoteDataSource
) {
    suspend operator fun invoke(muscle: String? = null, type: String? = null, difficulty: String? = null,
                                name: String? = null) {
        remote.sync(muscle = muscle,
            type = type,
            difficulty=difficulty,
            name = name)
    }
}