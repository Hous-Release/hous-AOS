package hous.release.domain.usecase.rule

import hous.release.domain.entity.rule.Rule
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class GetRulesUseCase @Inject constructor(private val repository: RuleRepository) {

    suspend operator fun invoke(): List<Rule> = repository.fetchRules()
}
