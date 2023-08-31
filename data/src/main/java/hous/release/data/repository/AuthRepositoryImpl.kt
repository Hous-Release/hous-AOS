package hous.release.data.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.LocalPrefTokenDataSource
import hous.release.data.datasource.LocalSplashDataSource
import hous.release.domain.entity.SplashState
import hous.release.domain.entity.Token
import hous.release.domain.entity.response.Login
import hous.release.domain.entity.response.SignUp
import hous.release.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPrefTokenDataSource: LocalPrefTokenDataSource,
    private val localSplashDataSource: LocalSplashDataSource
) : AuthRepository {
    override suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login> =
        kotlin.runCatching {
            authDataSource.postLogin(
                fcmToken,
                socialType,
                token
            )
        }.map { response -> response.data.toLogin() }

    override suspend fun postSignUp(
        birthday: String,
        isPublic: Boolean,
        nickname: String
    ): Result<SignUp> =
        kotlin.runCatching {
            authDataSource.postSignUp(
                birthday,
                localPrefTokenDataSource.fcmToken,
                isPublic,
                nickname,
                localPrefTokenDataSource.socialType,
                localPrefTokenDataSource.token
            )
        }.map { response -> response.data.toSignUp() }

    override fun initHousToken(token: Token) {
        localPrefTokenDataSource.accessToken = token.accessToken
        localPrefTokenDataSource.refreshToken = token.refreshToken
    }

    override fun initToken(
        fcmToken: String,
        socialType: String,
        token: String
    ) {
        localPrefTokenDataSource.fcmToken = fcmToken
        localPrefTokenDataSource.socialType = socialType
        localPrefTokenDataSource.token = token
    }

    override fun getFCMToken(setFCMToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e("Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                setFCMToken(task.result)
            }
        )
    }

    override suspend fun deleteUser(): Result<Unit> =
        kotlin.runCatching {
            authDataSource.deleteUser()
        }

    override suspend fun postLogout(): Result<Boolean> =
        kotlin.runCatching {
            authDataSource.postLogout()
        }.map { response ->
            response.success
        }

    override fun clearLocalPref() {
        authDataSource.clearLocalPref()
    }

    override fun getIsAccessToken(): Boolean = localPrefTokenDataSource.accessToken.isNotBlank()

    override fun setSplashState(splashState: SplashState) {
        localSplashDataSource.splashState = splashState.toString()
    }

    override fun getSplashState(): SplashState {
        return SplashState.valueOf(localSplashDataSource.splashState)
    }

    override suspend fun postForceLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login> =
        kotlin.runCatching {
            authDataSource.postForceLogin(
                fcmToken,
                socialType,
                token
            )
        }.map { response -> response.data.toLogin() }
}
