package hous.release.domain.usecase.search.strategy

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class MixedEnKrMatchStrategyTest {

    @Test
    fun `검색값이 target의 부분 문자열인지 확인한다`() {
        // given
        val mixedEnKrMatchStrategy = MixedEnKrMatchStrategy()
        val searchValue = "aa"
        val target = "caaabc"
        val searchValue2 = "가나다"
        val target2 = "가나다라마바사"
        val searchValue3 = "이준원murj"
        val target3 = "이준원murjune"
        // when
        val res = mixedEnKrMatchStrategy.isMatched(searchValue, target)
        val res2 = mixedEnKrMatchStrategy.isMatched(searchValue2, target2)
        val res3 = mixedEnKrMatchStrategy.isMatched(searchValue3, target3)
        // then
        assertAll(
            { assertThat(res).isEqualTo(true) },
            { assertThat(res2).isEqualTo(true) },
            { assertThat(res3).isEqualTo(true) }
        )
    }

    @Test
    fun `검색값에 한글초성이 존재하고 해당 위치의 target의 문자가 한글일 때, target문자의 초성과 일치 여부를 비교한다`() {
        // given
        val mixedEnKrMatchStrategy = MixedEnKrMatchStrategy()
        val searchValue = "ㅈㅃㅇㄱㅌㄱ"
        val target = "제빵왕김탁구"
        val searchValue2 = "ko틀ㄹ"
        val target2 = "ko틀린"
        // when
        val res = mixedEnKrMatchStrategy.isMatched(searchValue, target)
        val res2 = mixedEnKrMatchStrategy.isMatched(searchValue2, target2)
        // then
        assertAll(
            { assertThat(res).isEqualTo(true) },
            { assertThat(res2).isEqualTo(true) }
        )
    }

    @Test
    fun `search_value의_길이가_input의_길이보다_길면_false를_반환한다`() {
        // given
        val mixedEnKrMatchStrategy = MixedEnKrMatchStrategy()
        val searchValue = "이준원mur"
        val target = "이준원"
        // when
        val result = mixedEnKrMatchStrategy.isMatched(searchValue, target)
        // then
        assertThat(result).isEqualTo(false)
    }
}
