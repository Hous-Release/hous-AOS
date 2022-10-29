package hous.release.domain.usecase

import hous.release.domain.repository.OurRulesRepository
import javax.inject.Inject

class DeleteOurRulesUseCase @Inject constructor(private val ourRulesRepository: OurRulesRepository) {
    operator fun invoke(deleteRules: List<Int>) = ourRulesRepository.deleteRuleContent(deleteRules)
}
