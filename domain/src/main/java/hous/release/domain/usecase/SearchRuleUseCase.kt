package hous.release.domain.usecase

import hous.release.domain.entity.Rule
import javax.inject.Inject

class SearchRuleUseCase @Inject constructor() {
    operator fun <T : Rule> invoke(search: String, rules: List<T>): List<T> {
        val clear = search.lowercase().trim()
        return rules.filter { rule ->
            rule.name.lowercase().trim().contains(clear)
        }
    }
}
