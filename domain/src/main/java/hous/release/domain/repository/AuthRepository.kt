package hous.release.domain.repository

import hous.release.domain.entity.FeedbackType
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

    fun initHousToken(token: Token)

    fun initToken(fcmToken: String, socialType: String, token: String)

    suspend fun initSkipTutorial(skipTutorial: Boolean)

    suspend fun getFCMToken(setFCMToken: (String) -> Unit)

    suspend fun deleteUser(feedbackType: FeedbackType, comment: String): Result<Boolean>

    suspend fun postLogout(): Result<Boolean>

    fun clearLocalPref()

    suspend fun postForceLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login>
}
