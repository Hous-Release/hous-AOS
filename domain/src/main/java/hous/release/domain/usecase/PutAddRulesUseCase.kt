package hous.release.domain.usecase

import hous.release.domain.repository.OurRulesRepository
import javax.inject.Inject

class PutAddRulesUseCase @Inject constructor(private val ourRulesRepository: OurRulesRepository) {
    fun putAddRule(addedRuleList: List<String>) = ourRulesRepository.postAddedRule(addedRuleList)
}
