package hous.release.domain.usecase.rule

import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class UpdateRepresentRulesUseCase @Inject constructor(
    private val ruleRepository: RuleRepository
) {
    suspend operator fun invoke(rules: List<Int>) {
        ruleRepository.updateRepresentRules(rules)
    }
}
