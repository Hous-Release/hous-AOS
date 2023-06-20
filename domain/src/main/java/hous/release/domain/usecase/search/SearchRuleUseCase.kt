package hous.release.domain.usecase.search

import hous.release.domain.entity.Rule
import hous.release.domain.usecase.search.matcher.StringMatcher
import javax.inject.Inject

class SearchRuleUseCase @Inject constructor(
    private val matcher: StringMatcher // TODO 서버통신할 때, DI 모듈에 등록
) {
    operator fun <T : Rule> invoke(search: String, rules: List<T>): List<T> {
        return rules.filter { rule ->
            matcher.isMatched(search, rule.name)
        }
    }
}
