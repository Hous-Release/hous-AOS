package hous.release.domain.usecase.search.strategy

class SimpleMatchStrategy : RuleNameMatchStrategy {
    override fun isMatched(searchValue: String, rule: String): Boolean =
        rule.lowercase().trim().contains(searchValue.lowercase().trim())
}
