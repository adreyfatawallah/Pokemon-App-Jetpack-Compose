package com.example.pokemonapp

import android.app.Application
import com.example.pokemonapp.di.singleModule
import com.example.pokemonapp.di.viewModelModule
import com.skydoves.compose.stability.runtime.ComposeStabilityAnalyzer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ComposeStabilityAnalyzer.setEnabled(BuildConfig.DEBUG)

        startKoin {
            androidContext(this@MyApp)
            modules(singleModule)
            modules(viewModelModule)
        }
    }
}