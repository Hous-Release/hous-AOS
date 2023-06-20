package hous.release.domain.usecase.search.matcher

import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import hous.release.domain.usecase.search.strategy.RuleNameMatchStrategy

class RuleNameMatcher(
    private val ruleNameMatchStrategy: RuleNameMatchStrategy = MixedEnKrMatchStrategy()
) : StringMatcher {
    override fun isMatched(searchValue: String, targetValue: String): Boolean {
        return ruleNameMatchStrategy.isMatched(
            searchValue.clear(),
            targetValue.clear()
        )
    }

    /**
     * 공백 제거, 특수문자 제거, lowerCase
     * */
    private fun String.clear(): String =
        this.replace("\\s".toRegex(), "")
            .replace("[^a-zA-Z0-9가-힣]".toRegex(), "")
            .lowercase()
}
