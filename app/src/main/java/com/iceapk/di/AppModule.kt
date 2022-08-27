package com.iceapk.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentFactory
import com.iceapk.presentation.ICEFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    @Named("App")
    fun providesApplication(@ApplicationContext app: Context): Application {
        return app as Application
    }

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideFragmentFactory(): FragmentFactory {
        return ICEFragmentFactory()
    }
}