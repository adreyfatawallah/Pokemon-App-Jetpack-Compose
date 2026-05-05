package com.example.pokemonapp

import android.app.Application
import com.example.pokemonapp.feature.auth.authModule
import com.example.pokemonapp.feature.pokemon.pokemonModule
import com.skydoves.compose.stability.runtime.ComposeStabilityAnalyzer
import io.kotzilla.generated.monitoring
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ComposeStabilityAnalyzer.setEnabled(BuildConfig.DEBUG)

        startKoin {
            androidContext(this@MyApp)

            androidLogger()
            monitoring()

            modules(myModule)
            modules(authModule)
            modules(pokemonModule)
        }
    }
}