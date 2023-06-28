package hous.release.domain.usecase.search.matcher

fun interface StringMatcher {
    fun isMatched(searchValue: String, targetValue: String): Boolean
}
