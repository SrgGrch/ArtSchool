package com.longterm.artschools

import android.app.Application
import com.longterm.artschools.di.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArtSchoolsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArtSchoolsApp)

            modules(moduleList)
        }
    }
}