package hous.release.domain.repository

import hous.release.domain.entity.request.DomainLoginRequest
import hous.release.domain.entity.response.DomainLoginResponse

interface AuthRepository {
    suspend fun postLogin(loginRequest: DomainLoginRequest): Result<DomainLoginResponse>

    suspend fun initSkipTutorial(skipTutorial: Boolean)
}
