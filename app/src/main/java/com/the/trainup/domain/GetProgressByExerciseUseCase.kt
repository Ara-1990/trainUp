package com.the.trainup.domain


class GetProgressByExerciseUseCase(
    private val setsRepo: SetRepository
)
 {
    suspend operator fun invoke(exerciseId: Long): List<SetEntry> {
        return setsRepo.getSetsByExercise(exerciseId)
    }
}
