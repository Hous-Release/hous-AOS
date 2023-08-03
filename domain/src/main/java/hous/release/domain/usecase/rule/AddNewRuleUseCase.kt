package hous.release.domain.usecase.rule

import hous.release.domain.enums.PhotoUri
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class AddNewRuleUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val ourRulesRepository: RuleRepository
) {
    suspend operator fun invoke(
        description: String,
        name: String,
        imageUri: List<PhotoUri>
    ): Boolean {
        val localFiles = photoRepository.fetchPhotosByUri(imageUri)
        ourRulesRepository.postAddedRule(description, name, localFiles)
        return photoRepository.removeTemporayPhotos()
    }
}
