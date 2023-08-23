package hous.release.domain.usecase.rule

import hous.release.domain.entity.rule.RepresentativeRule
import hous.release.domain.repository.RuleRepository
import javax.inject.Inject

class GetRepresentativeRulesUseCase @Inject constructor(private val repository: RuleRepository) {

    suspend operator fun invoke(): List<RepresentativeRule> =
        repository.fetchMainRules().map { mainRule ->
            RepresentativeRule(
                id = mainRule.id,
                name = mainRule.name,
                isRepresent = mainRule.isRepresent
            )
        }
}
