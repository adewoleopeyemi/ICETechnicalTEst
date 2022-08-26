package com.iceapk.presentation.di

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.iceapk.network.interfaces.ICEService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://fakestoreapi.com/"


    @Singleton
    @Provides
    @Named("ICE")
    fun provideApiService(
        @Named("ICEService")retrofit: Retrofit): ICEService {
        return retrofit.create(ICEService::class.java)
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named("ICEService")
    fun providesRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    @Named("Volley")
    fun providesVolley(
        context: Application
    ): RequestQueue {
        return Volley.newRequestQueue(context)
    }

}
