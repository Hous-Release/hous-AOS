package hous.release.domain.usecase

import hous.release.domain.repository.PersonalityRepository
import javax.inject.Inject

class GetPersonalityResultUseCase @Inject constructor(
    private val personalityRepository: PersonalityRepository
) {
    suspend operator fun invoke(color: String) = personalityRepository.getPersonalityResult(color)
}
