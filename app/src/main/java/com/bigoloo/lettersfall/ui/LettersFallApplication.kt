package com.bigoloo.lettersfall.ui

import android.app.Application
import com.bigoloo.lettersfall.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LettersFallApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@LettersFallApplication)
            modules(appModule)
        }
    }
}