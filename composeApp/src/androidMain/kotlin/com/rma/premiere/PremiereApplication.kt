package com.rma.premiere

import android.app.Application
import com.rma.premiere.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PremiereApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PremiereApplication)
            modules(appModule)
        }
    }
}