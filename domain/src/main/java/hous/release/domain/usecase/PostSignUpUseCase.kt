package hous.release.domain.usecase

import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(birthday: String, isPublic: Boolean, nickname: String) =
        authRepository.postSignUp(birthday, isPublic, nickname)
}
