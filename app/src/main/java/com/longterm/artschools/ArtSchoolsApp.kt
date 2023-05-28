package com.longterm.artschools

import android.app.Application
import com.longterm.artschools.di.moduleList
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArtSchoolsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("8f929c7e-8c3f-4f38-851b-61358d561919")
        MapKitFactory.initialize(this)

        startKoin {
            androidContext(this@ArtSchoolsApp)

            modules(moduleList)
        }
    }
}