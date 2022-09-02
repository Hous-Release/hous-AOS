package hous.release.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hous.release.android.util.binding.HousDeBugTree
import timber.log.Timber

@HiltAndroidApp
class HousApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(HousDeBugTree())
    }
}
