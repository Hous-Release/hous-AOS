package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(setFCMToken: (String) -> Unit) =
        authRepository.getFCMToken(setFCMToken)
}
