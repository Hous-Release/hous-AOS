package hous.release.domain.usecase.search.strategy

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class SimpleMatchStrategyTest {

    @Test
    fun `검색값이 target의 부분 문자열인지 확인한다`() {
        // given
        val simpleMatchStrategy = SimpleMatchStrategy()
        val searchValue = "aa"
        val target = "caaabc"
        val searchValue2 = "가나다"
        val target2 = "가나다라마바사"
        val searchValue3 = "이준원murj"
        val target3 = "이준원murjune"
        // when
        val res = simpleMatchStrategy.isMatched(searchValue, target)
        val res2 = simpleMatchStrategy.isMatched(searchValue2, target2)
        val res3 = simpleMatchStrategy.isMatched(searchValue3, target3)
        // then
        assertAll(
            { assertThat(res).isEqualTo(true) },
            { assertThat(res2).isEqualTo(true) },
            { assertThat(res3).isEqualTo(true) }
        )
    }
}
