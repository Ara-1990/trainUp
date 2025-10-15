package com.the.trainup.di

import android.content.Context
import androidx.room.Room
import com.the.trainup.data.AppDb
import com.the.trainup.data.ExerciseDao
import com.the.trainup.data.ExerciseRepositoryImpl
import com.the.trainup.data.SetDao
import com.the.trainup.data.SetRepositoryImpl
import com.the.trainup.domain.AddSetUseCase
import com.the.trainup.domain.ExerciseRepository
import com.the.trainup.domain.GetProgressByExerciseUseCase
import com.the.trainup.domain.SetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDb =
        Room.databaseBuilder(ctx, AppDb::class.java, "fitness.db").build()

    @Provides
    fun provideExerciseDao(db: AppDb) = db.exerciseDao()

    @Provides
    fun provideSetDao(db: AppDb) = db.setDao()

    @Provides @Singleton
    fun provideExerciseRepo(dao: ExerciseDao): ExerciseRepository =
        ExerciseRepositoryImpl(dao)


    @Provides @Singleton
    fun provideSetRepo(dao: SetDao): SetRepository =
        SetRepositoryImpl(dao)

    @Provides fun provideAddSetUseCase(repo: SetRepository) = AddSetUseCase(repo)

    @Provides fun provideGetProgressUseCase(repo: SetRepository) =
        GetProgressByExerciseUseCase(repo)


    @Provides fun provideSyncExercisesUseCase(remote: com.the.trainup.data.remote.ExerciseRemoteDataSource) =
        com.the.trainup.domain.SyncExercisesUseCase(remote)

}