package com.iceapk.di

import com.iceapk.network.interfaces.ICEService
import com.iceapk.repository.login.LoginRepo
import com.iceapk.repository.login.LoginRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Singleton
    @Provides
    fun providesLoginRepo(@Named("ICE") service: ICEService): LoginRepo {
        return LoginRepoImpl(service)
    }
}