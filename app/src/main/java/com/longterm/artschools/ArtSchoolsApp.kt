package com.longterm.artschools

import android.app.Application
import com.longterm.artschools.di.commonModule
import com.longterm.artschools.di.dataModule
import com.longterm.artschools.di.domainModule
import com.longterm.artschools.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArtSchoolsApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArtSchoolsApp)

            modules(
                commonModule,
                domainModule,
                dataModule,
                presentationModule,
            )
        }
    }
}