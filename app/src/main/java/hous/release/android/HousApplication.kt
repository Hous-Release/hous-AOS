package hous.release.android

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import hous.release.android.BuildConfig.KAKAO_NATIVE_APP_KEY
import hous.release.android.util.HousDeBugTree
import timber.log.Timber

@HiltAndroidApp
class HousApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
        if (BuildConfig.DEBUG) Timber.plant(HousDeBugTree())
    }
}
