package com.the.trainup.data.remote


import com.the.trainup.data.remote.dto.ExerciseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NinjasApi {
    @GET("v1/exercises")
    suspend fun getExercises(
        @Query("muscle") muscle: String? = null,
        @Query("type") type: String? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("name") name: String? = null
    ): List<ExerciseDto>

}