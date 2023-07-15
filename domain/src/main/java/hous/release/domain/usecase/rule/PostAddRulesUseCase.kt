package hous.release.domain.usecase.rule

import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class PostAddRulesUseCase @Inject constructor(private val ourRulesRepository: RuleRepository) {
    suspend operator fun invoke(addedRuleList: List<String>) =
        ourRulesRepository.postAddedRule(addedRuleList)
}
