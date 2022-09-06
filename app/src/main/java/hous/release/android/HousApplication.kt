package hous.release.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import hous.release.android.util.binding.HousDeBugTree
import timber.log.Timber

@HiltAndroidApp
class HousApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))
        if (BuildConfig.DEBUG) Timber.plant(HousDeBugTree())
    }
}
