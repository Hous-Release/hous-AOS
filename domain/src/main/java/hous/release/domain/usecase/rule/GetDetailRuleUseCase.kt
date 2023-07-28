package hous.release.domain.usecase.rule

import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class GetDetailRuleUseCase @Inject constructor(private val repository: RuleRepository) {

    suspend operator fun invoke(id: Int): DetailRule = repository.fetchDetailRule(id)
}
