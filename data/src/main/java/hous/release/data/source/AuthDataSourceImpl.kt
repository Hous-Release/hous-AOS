package hous.release.data.source

import hous.release.data.model.WrapperClass
import hous.release.data.model.request.LoginRequest
import hous.release.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun login(loginRequest: LoginRequest): WrapperClass<Any> =
        authService.kakaoLogin(loginRequest)
}
