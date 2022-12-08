package hous.release.android

import android.app.Application
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.HiltAndroidApp
import hous.release.android.BuildConfig.KAKAO_NATIVE_APP_KEY
import hous.release.android.util.HousDeBugTree
import timber.log.Timber

@HiltAndroidApp
class HousApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        var keyHash = Utility.getKeyHash(this)
        Timber.d("keyHash $keyHash")
        Log.d("KEYHASH", "KEYHASH: $keyHash")
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
        if (BuildConfig.DEBUG) Timber.plant(HousDeBugTree())
        else Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
    }
}
