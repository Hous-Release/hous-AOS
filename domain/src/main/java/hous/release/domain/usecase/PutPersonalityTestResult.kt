package hous.release.domain.usecase

import hous.release.domain.repository.PersonalityRepository
import javax.inject.Inject

class PutPersonalityTestResult @Inject constructor(
    private val personalityRepository: PersonalityRepository
) {
    suspend operator fun invoke(
        cleanScore: Int,
        introversionScore: Int,
        lightScore: Int,
        noiseScore: Int,
        smellScore: Int
    ) = personalityRepository.putPersonalityTestResult(
        cleanScore = cleanScore,
        introversionScore = introversionScore,
        lightScore = lightScore,
        noiseScore = noiseScore,
        smellScore = smellScore
    )
}
