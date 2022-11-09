package hous.release.domain.usecase

import hous.release.domain.repository.ProfileRepository
import javax.inject.Inject

class GetHomieProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(homieId: Int) = profileRepository.getHomieProfile(homieId)
}
