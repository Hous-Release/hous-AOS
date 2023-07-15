package hous.release.domain.usecase

import hous.release.domain.entity.rule.MainRule
import hous.release.domain.repository.OurRulesRepository
import javax.inject.Inject

class PutEditOurRulesUseCase @Inject constructor(
    private val repository: OurRulesRepository
) {
    operator fun invoke(editedRules: List<MainRule>) =
        repository.putEditedRuleContent(editedRules)
}
