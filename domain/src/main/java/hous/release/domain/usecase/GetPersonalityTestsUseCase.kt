package hous.release.domain.usecase

import hous.release.domain.repository.PersonalityRepository
import javax.inject.Inject

class GetPersonalityTestsUseCase @Inject constructor(
    private val personalityRepository: PersonalityRepository
) {
    suspend operator fun invoke() = personalityRepository.getPersonalityTests()
}
