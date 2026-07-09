package com.majasociet.nafusitemobileapp

import android.app.Application
import android.util.Log
import timber.log.Timber

/**
 * Application class for the app
 */
class NafusiteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("NafusiteApplication", "onCreate called")

        // Plant the tree EXACTLY ONCE globally here!
        if (com.majasociet.nafusitemobileapp.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber has been planted in Debug mode")
        }
    }
}