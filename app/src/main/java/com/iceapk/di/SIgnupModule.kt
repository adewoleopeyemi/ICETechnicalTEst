package com.iceapk.presentation.di

import com.iceapk.network.interfaces.ICEService
import com.iceapk.repository.signup.SignupRepo
import com.iceapk.repository.signup.SignupRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SIgnupModule {

    @Provides
    @Singleton
    fun providesRepository(@Named("ICE") service: ICEService): SignupRepo {
        return SignupRepoImpl(service)
    }
}