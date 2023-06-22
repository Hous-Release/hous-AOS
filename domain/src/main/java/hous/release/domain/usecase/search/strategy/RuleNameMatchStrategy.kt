package hous.release.domain.usecase.search.strategy

fun interface RuleNameMatchStrategy {
    fun isMatched(searchValue: String, rule: String): Boolean
}
