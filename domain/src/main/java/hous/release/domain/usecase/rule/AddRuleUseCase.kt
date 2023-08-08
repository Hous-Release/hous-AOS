package hous.release.domain.usecase.rule

import hous.release.domain.entity.Photo
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class AddRuleUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val ruleRepository: RuleRepository
) {
    suspend operator fun invoke(
        description: String,
        name: String,
        photos: List<Photo>
    ): Boolean {
        val localFiles = photoRepository.fetchPhotosBy(photos)
        ruleRepository.addRule(description, name, localFiles)
        return photoRepository.removeTemporayPhotos()
    }
}
