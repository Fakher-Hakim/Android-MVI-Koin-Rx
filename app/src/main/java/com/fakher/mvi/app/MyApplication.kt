package com.fakher.mvi.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.fakher.mvi.koin.appModule
import com.fakher.mvi.koin.viewModelModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        startKoin(this, listOf(appModule, viewModelModule))
    }
}