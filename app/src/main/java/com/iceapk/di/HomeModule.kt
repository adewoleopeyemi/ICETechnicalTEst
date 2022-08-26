package com.iceapk.presentation.di

import com.iceapk.network.interfaces.ICEService
import com.iceapk.repository.home.HomeRepo
import com.iceapk.repository.home.HomeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun providesHomeRepo( service: ICEService): HomeRepo {
        return HomeRepoImpl(service)
    }
}