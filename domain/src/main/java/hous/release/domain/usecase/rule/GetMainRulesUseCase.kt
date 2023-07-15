package hous.release.domain.usecase.rule

import hous.release.domain.entity.rule.MainRule
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class GetMainRulesUseCase @Inject constructor(private val repository: RuleRepository) {

    suspend operator fun invoke(): List<MainRule> = repository.fetchMainRules()
}
