package com.the.trainup.di

import com.squareup.moshi.Moshi
import com.the.trainup.data.remote.NinjasApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.the.trainup.BuildConfig


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .addInterceptor { chain ->

                val req = chain.request().newBuilder()
                    .addHeader("X-Api-Key", BuildConfig.NINJAS_API_KEY)
                    .build()
                chain.proceed(req)
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(ok: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(ok)
            .build()
    }

    @Provides
    @Singleton
    fun provideNinjasApi(r: Retrofit): NinjasApi = r.create(NinjasApi::class.java)
}

