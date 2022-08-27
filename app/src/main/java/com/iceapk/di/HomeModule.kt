package com.iceapk.di

import com.iceapk.data.dao.CartsDao
import com.iceapk.data.dao.ProductsDao
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
    fun providesHomeRepo( @Named("ICE")service: ICEService, dao: ProductsDao, cartsDao: CartsDao): HomeRepo {
        return HomeRepoImpl(service, dao, cartsDao)
    }
}