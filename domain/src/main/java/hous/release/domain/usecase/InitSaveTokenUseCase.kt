package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class InitSaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        fcmToken: String?,
        socialType: String?,
        token: String?
    ) = authRepository.initSaveToken(fcmToken, socialType, token)
}
