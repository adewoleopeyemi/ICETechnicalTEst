package com.iceapk.di

import com.iceapk.data.dao.CartsDao
import com.iceapk.repository.cart.CartRepo
import com.iceapk.repository.cart.CartRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Singleton
    @Provides
    fun providesCartsRepo(dao: CartsDao): CartRepo{
        return CartRepoImpl(dao)
    }
}