package hous.release.data.remote.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoLoginService @Inject constructor(@ActivityContext private val context: Context) {
    fun startKakaoLogin(kakaoLoginCallBack: (OAuthToken?, Throwable?) -> Unit) {
        val kakaoLoginState =
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) KAKAO_LOGIN
            else KAKAO_ACCOUNT_LOGIN

        when (kakaoLoginState) {
            KAKAO_LOGIN -> UserApiClient.instance.loginWithKakaoTalk(
                context,
                callback = kakaoLoginCallBack
            )
            KAKAO_ACCOUNT_LOGIN -> UserApiClient.instance.loginWithKakaoAccount(
                context,
                callback = kakaoLoginCallBack
            )
        }
    }

    companion object {
        const val KAKAO_LOGIN = 0
        const val KAKAO_ACCOUNT_LOGIN = 1
    }
}
