package hous.release.data.repository

import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.SharedPrefDataSource
import hous.release.data.entity.request.LoginRequest
import hous.release.domain.entity.Token
import hous.release.domain.entity.request.DomainLoginRequest
import hous.release.domain.entity.response.DomainLoginResponse
import hous.release.domain.repository.AuthRepository
import java.lang.IllegalStateException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource
) : AuthRepository {
    override suspend fun postLogin(loginRequest: DomainLoginRequest): Result<DomainLoginResponse> {
        kotlin.runCatching {
            authDataSource.postLogin(
                LoginRequest(
                    fcmToken = loginRequest.fcmToken,
                    socialType = loginRequest.socialType,
                    token = loginRequest.token
                )
            )
        }.onSuccess { response ->
            DomainLoginResponse(
                isJoiningRoom = response.data.isJoiningRoom,
                token = Token(
                    response.data.token.accessToken,
                    response.data.token.refreshToken
                ),
                userId = response.data.userId
            )
        }.onFailure { throw it }
        throw IllegalStateException(UNKNOWN_ERROR)
    }

    override suspend fun initShowTutorial(skipTutorial: Boolean) {
        sharedPrefDataSource.prefs.edit().putBoolean(SKIP_TUTORIAL, skipTutorial).apply()
    }

    override suspend fun getShowTutorial(): Boolean {
        return sharedPrefDataSource.prefs.getBoolean(SKIP_TUTORIAL, false)
    }

    companion object {
        private const val UNKNOWN_ERROR = "네트워크 통신 중 알 수 없는 오류"
        private const val REFRESH_TOKEN = 0
        private const val ACCESS_TOKEN = 1
        private const val SKIP_TUTORIAL = "SkipTutorial"
    }
}
