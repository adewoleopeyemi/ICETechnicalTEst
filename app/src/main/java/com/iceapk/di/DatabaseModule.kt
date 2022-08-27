package com.iceapk.di

import android.content.Context
import androidx.room.RoomDatabase
import com.iceapk.data.dao.Database
import com.iceapk.data.dao.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesCollectionsDao(
        database: Database
    ): ProductsDao{
        return database.collectionsDao()
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): Database {
        return Database.getDatabase(appContext)
    }
}