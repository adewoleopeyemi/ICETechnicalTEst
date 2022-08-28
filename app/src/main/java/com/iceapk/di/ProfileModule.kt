package com.iceapk.di

import com.iceapk.network.interfaces.ICEService
import com.iceapk.repository.login.LoginRepo
import com.iceapk.repository.login.LoginRepoImpl
import com.iceapk.repository.profile.ProfileRepo
import com.iceapk.repository.profile.ProfileRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Singleton
    @Provides
    fun providesProfileRepo(@Named("ICE") service: ICEService): ProfileRepo {
        return ProfileRepoImpl(service)
    }
}