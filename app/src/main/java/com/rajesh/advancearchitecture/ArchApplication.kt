package com.rajesh.advancearchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}