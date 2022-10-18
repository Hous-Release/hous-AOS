package hous.release.data.datasource

import hous.release.data.entity.request.LoginRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.LoginResponse
import hous.release.data.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Response<BaseResponse<LoginResponse>> =
        authService.postLogin(LoginRequest(fcmToken, socialType, token))
}
