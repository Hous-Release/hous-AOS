package hous.release.domain.usecase.rule

import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class DeleteRuleUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val ruleRepository: RuleRepository
) {
    suspend operator fun invoke(ruleId: Int, photoPaths: List<String>) {
        ruleRepository.deleteRule(ruleId)
        photoPaths.forEach { path -> photoRepository.removePhoto(path) }
    }
}
