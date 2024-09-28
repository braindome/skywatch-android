package se.braindome.skywatch

import android.app.Application
import timber.log.Timber

class SkywatchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}