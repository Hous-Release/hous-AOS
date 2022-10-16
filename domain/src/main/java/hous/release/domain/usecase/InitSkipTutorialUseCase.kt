package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class InitSkipTutorialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(skipTutorial: Boolean) =
        authRepository.initShowTutorial(skipTutorial)
}
