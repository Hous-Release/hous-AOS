package hous.release.domain.usecase

import hous.release.domain.entity.Token
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class InitHousTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: Token) = authRepository.initHousToken(token)
}
