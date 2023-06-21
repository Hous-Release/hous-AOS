package hous.release.domain.usecase.search.matcher

import hous.release.domain.usecase.search.strategy.RuleNameMatchStrategy
import javax.inject.Inject

class RuleNameMatcher @Inject constructor(
    private val ruleNameMatchStrategy: RuleNameMatchStrategy
) : StringMatcher {
    override fun isMatched(searchValue: String, targetValue: String): Boolean {
        return ruleNameMatchStrategy.isMatched(
            searchValue.mapToSearchFormat(),
            targetValue.mapToSearchFormat()
        )
    }

    /**
     * 공백 제거, 특수문자 제거, lowerCase
     * */
    private fun String.mapToSearchFormat(): String =
        this.replace("\\s".toRegex(), "")
            .replace("[^a-zA-Z0-9가-힣]".toRegex(), "")
            .lowercase()
}
