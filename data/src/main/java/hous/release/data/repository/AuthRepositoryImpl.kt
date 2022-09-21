package hous.release.data.repository

import hous.release.data.datasource.AuthDataSource
import hous.release.data.entity.request.LoginRequest
import hous.release.data.entity.response.LoginResponse
import hous.release.domain.entity.response.Login
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login> {
        kotlin.runCatching {
            authDataSource.postLogin(
                LoginRequest(
                    fcmToken = fcmToken,
                    socialType = socialType,
                    token = token
                )
            )
        }.onSuccess { response ->
            return Result.success(
                DomainLoginResponse(
                    token = Token(
                        response.data.token.accessToken,
                        response.data.token.refreshToken
                    ),
                    userId = response.data.userId
                )
            )
        }.onFailure { throw it }
        throw IllegalStateException(UNKNOWN_ERROR)
    }

    companion object {
        const val UNKNOWN_ERROR = "네트워크 통신 중 알 수 없는 오류"
    }
}
