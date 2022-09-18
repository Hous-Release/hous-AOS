package hous.release.data.repository

import hous.release.data.datasource.AuthDataSource
import hous.release.data.entity.request.LoginRequest
import hous.release.domain.entity.request.DomainLoginRequest
import hous.release.domain.entity.response.DomainLoginResponse
import hous.release.domain.entity.response.Token
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
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
