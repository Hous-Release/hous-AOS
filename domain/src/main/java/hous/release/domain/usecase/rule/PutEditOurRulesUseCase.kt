package hous.release.domain.usecase.rule

import hous.release.domain.entity.rule.MainRule
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class PutEditOurRulesUseCase @Inject constructor(
    private val repository: RuleRepository
) {
    operator fun invoke(editedRules: List<MainRule>) =
        repository.putEditedRuleContent(editedRules)
}
