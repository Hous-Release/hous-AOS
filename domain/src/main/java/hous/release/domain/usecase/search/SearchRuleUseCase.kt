package hous.release.domain.usecase.search

import hous.release.domain.entity.BaseRule
import hous.release.domain.usecase.search.matcher.StringMatcher
import javax.inject.Inject

class SearchRuleUseCase @Inject constructor(
    private val matcher: StringMatcher
) {
    operator fun <T : BaseRule> invoke(search: String, rules: List<T>): List<T> {
        return rules.filter { rule ->
            matcher.isMatched(search, rule.name)
        }
    }
}
