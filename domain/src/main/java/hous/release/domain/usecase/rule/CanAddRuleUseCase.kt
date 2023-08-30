package hous.release.domain.usecase.rule

import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class CanAddRuleUseCase @Inject constructor(
    private val ruleRepository: RuleRepository
) {
    suspend operator fun invoke(): Boolean = ruleRepository.canAddRule()
}
