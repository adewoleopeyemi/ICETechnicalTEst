package com.iceapk.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ICEApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}