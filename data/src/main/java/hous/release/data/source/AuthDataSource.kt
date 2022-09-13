package hous.release.data.source

import hous.release.data.model.WrapperClass
import hous.release.data.model.request.LoginRequest

interface AuthDataSource {
    suspend fun login(loginRequest: LoginRequest): WrapperClass<Any>
}
