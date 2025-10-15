package com.the.trainup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    suspend fun getAll(): List<ExerciseEntity>


    @Insert
    suspend fun insert(entity: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ExerciseEntity>)


    @Query("SELECT id FROM exercises WHERE name = :name AND muscleGroup = :muscle LIMIT 1")
    suspend fun findIdByNameAndMuscle(name: String, muscle: String): Long?

}


@Dao
interface SetDao {
    @Insert
    suspend fun insert(entity: SetEntity)

    @Query("SELECT * FROM sets WHERE workoutId = :id ORDER BY id")
    suspend fun getByWorkout(id: Long): List<SetEntity>

    @Query("""
        SELECT s.* FROM sets s
        JOIN workouts w ON w.id = s.workoutId
        WHERE s.exerciseId = :exerciseId
        ORDER BY w.date ASC, s.id ASC
    """)
    suspend fun getByExercise(exerciseId: Long): List<SetEntity>

}