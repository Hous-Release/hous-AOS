package hous.release.domain.usecase

import hous.release.domain.entity.SplashState
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class SetSplashStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(splashState: SplashState) {
        authRepository.setSplashState(splashState)
    }
}
