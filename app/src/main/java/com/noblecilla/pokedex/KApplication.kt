package com.noblecilla.pokedex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.noblecilla.pokedex.di.appModule
import com.noblecilla.pokedex.view.setting.Mode
import com.noblecilla.pokedex.view.setting.Preferences
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            modules(appModule)
        }

        val nightMode = when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
            Mode.NIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
