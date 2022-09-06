package hous.release.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "23a6d7ad94f44f0e474ee41b4e6d9fab")
    }
}
