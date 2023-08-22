package hous.release.domain.usecase.rule

import hous.release.domain.entity.Photo
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class UpdateRuleUseCase @Inject constructor(
    private val ruleRepository: RuleRepository,
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(
        id: Int,
        description: String,
        name: String,
        photos: List<Photo>
    ): Boolean {
        val localFiles = photoRepository.fetchPhotosBy(photos)
        ruleRepository.updateRule(id, description, name, localFiles)
        return photoRepository.removeTemporayPhotos()
    }
}
