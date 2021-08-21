package com.psquare.setup

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.psquare.setup.di.AppModule
import com.psquare.setup.di.NetworkModule
import com.psquare.setup.di.PrefsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App: Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        plantTimber()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.INFO else Level.NONE)
            androidContext(this@App)
            modules(listOf(PrefsModule, NetworkModule, AppModule))
        }
    }

    private fun plantTimber() {
        Timber.plant(if (BuildConfig.DEBUG) DebugTree() else ReleaseTree())
    }
}

class ReleaseTree: Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            // TODO: Log the exception using firebase crashlytics
        }
    }
}

class DebugTree: Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "%s @ (Line no. %s) ::",
            super.createStackElementTag(element),
            element.lineNumber
        )
    }
}