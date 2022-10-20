package hous.release.domain.usecase

import hous.release.domain.repository.OurRulesRepository
import javax.inject.Inject

class PostAddRulesUseCase @Inject constructor(private val ourRulesRepository: OurRulesRepository) {
    operator fun invoke(addedRuleList: List<String>) =
        ourRulesRepository.postAddedRule(addedRuleList)
}
