package com.uruklabs.dictionaryapp.application

import android.app.Application
import com.uruklabs.dictionaryapp.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DicionaryAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DicionaryAplication)
            koin.loadModules(listOf(appModule))

        }
    }

}