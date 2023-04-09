package hous.release.domain.repository

import hous.release.domain.entity.FeedbackType
import hous.release.domain.entity.SplashState
import hous.release.domain.entity.Token
import hous.release.domain.entity.response.Login
import hous.release.domain.entity.response.SignUp

interface AuthRepository {
    suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login>

    suspend fun postSignUp(
        birthday: String,
        isPublic: Boolean,
        nickname: String
    ): Result<SignUp>

    fun initLoginToken(fcmToken: String, socialType: String, token: String)

    fun initHousToken(token: Token)

    fun getFCMToken(setFCMToken: (String) -> Unit)

    suspend fun deleteUser(feedbackType: FeedbackType, comment: String): Result<Boolean>

    suspend fun postLogout(): Result<Boolean>

    fun clearLocalPref()

    fun getIsAccessToken(): Boolean

    fun setSplashState(splashState: SplashState)

    fun getSplashState(): SplashState

    suspend fun postForceLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login>
}
