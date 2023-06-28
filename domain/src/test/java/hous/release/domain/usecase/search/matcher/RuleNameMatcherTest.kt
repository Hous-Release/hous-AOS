package hous.release.domain.usecase.search.matcher

import com.google.common.truth.Truth.assertThat
import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RuleNameMatcherTest {

    @Test
    fun `직접 match 전략을 수정할 수 있다`() {
        // given
        val ruleNameMatcher = RuleNameMatcher { s, t -> t.contains(s) }

        val searchValue = "KW"
        val targetValue = "KWY"
        // when
        val res = ruleNameMatcher.isMatched(searchValue, targetValue)
        // then
        assertThat(res).isEqualTo(true)
    }

    @Test
    fun `공백, 특수문자, 대소문자를 제거한 후 문자열을 비교한다`() {
        // given
        val ruleNameMatcher = RuleNameMatcher(MixedEnKrMatchStrategy())
        // when
        val res = ruleNameMatcher.isMatched(" a#b*c& ", "a%b%c")
        val res2 = ruleNameMatcher.isMatched("ㅇㅈㅐ", "이준원")
        // then
        assertAll(
            { assertThat(res).isEqualTo(true) },
            { assertThat(res2).isEqualTo(true) }
        )
    }
}
