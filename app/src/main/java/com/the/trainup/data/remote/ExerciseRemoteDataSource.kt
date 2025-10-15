package com.the.trainup.data.remote
import com.the.trainup.data.AppDb
import com.the.trainup.data.ExerciseDao

import com.the.trainup.data.toEntity
import javax.inject.Inject

class ExerciseRemoteDataSource @Inject constructor(
        private val api: NinjasApi,
        private val db: AppDb
    ) {

        suspend fun sync(muscle: String? = null, type: String? = null, difficulty:String? = null,
                         name:String? = null) {

            try {

                val exercises = when {
                    !name.isNullOrBlank() -> api.getExercises(name = name)
                    !muscle.isNullOrBlank() -> api.getExercises(muscle = muscle)
                    !type.isNullOrBlank() -> api.getExercises(type = type)
                    !difficulty.isNullOrBlank() -> api.getExercises(difficulty = difficulty)
                    else -> api.getExercises()
                }


                db.exerciseDao().insertAll(
                    exercises.map { it.toEntity() }
                )

            } catch (e: Exception) {
                e.printStackTrace()

                throw e
            }

        }
    }

