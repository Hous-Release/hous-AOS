package hous.release.domain.usecase.rule

import hous.release.domain.value.PhotoUri
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class AddRuleUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val ourRulesRepository: RuleRepository
) {
    suspend operator fun invoke(
        description: String,
        name: String,
        imageUri: List<PhotoUri>
    ): Boolean {
        val localFiles = photoRepository.fetchPhotosByUri(imageUri)
        ourRulesRepository.addRule(description, name, localFiles)
        return photoRepository.removeTemporayPhotos()
    }
}
