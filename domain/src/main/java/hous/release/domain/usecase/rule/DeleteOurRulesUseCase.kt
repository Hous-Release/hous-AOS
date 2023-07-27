package hous.release.domain.usecase.rule

import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class DeleteOurRulesUseCase @Inject constructor(private val ourRulesRepository: RuleRepository) {
    operator fun invoke(deleteRules: List<Int>) = ourRulesRepository.deleteRuleContent(deleteRules)
}
