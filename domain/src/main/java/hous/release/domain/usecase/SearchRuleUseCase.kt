package hous.release.domain.usecase

import hous.release.domain.entity.Rule
import javax.inject.Inject

class SearchRuleUseCase @Inject constructor() {
    operator fun invoke(search: String, rules: List<Rule>): List<Rule> {
        val clear = search.lowercase().trim()
        return rules.filter {
            it.name.lowercase().trim().contains(clear)
        }
    }
}
