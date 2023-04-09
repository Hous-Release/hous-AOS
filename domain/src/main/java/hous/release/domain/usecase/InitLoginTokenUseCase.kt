package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class InitLoginTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(fcmToken: String, socialType: String, token: String) =
        authRepository.initLoginToken(fcmToken, socialType, token)
}
