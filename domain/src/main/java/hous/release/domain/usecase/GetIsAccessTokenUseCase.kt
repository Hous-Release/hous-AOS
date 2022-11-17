package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class GetIsAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getIsAccessToken()
}
