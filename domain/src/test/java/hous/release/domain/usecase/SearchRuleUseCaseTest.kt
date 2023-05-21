package hous.release.domain.usecase

import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.Rule
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class SearchRuleUseCaseTest {

    data class MainTodo(
        val isNew: Boolean,
        override val id: Int,
        override val name: String
    ) : Rule(id, name)

    private val searchUseCase = SearchRuleUseCase()

    @Test
    @DisplayName("영어 rule name 반환")
    fun useCaseTest() {
        // given
        val expectList =
            listOf(MainTodo(true, 1, "KWY"), MainTodo(true, 2, "KWY2"), MainTodo(true, 1, "LJW"))
        // when
        val result: List<MainTodo> = searchUseCase("kw", expectList).filterIsInstance<MainTodo>()
        // then
        assertThat(result).isEqualTo(listOf(MainTodo(true, 1, "KWY"), MainTodo(true, 2, "KWY2")))
    }

    @Test
    @DisplayName("한국 rule name 반환")
    fun useCaseTest2() {
        // given
        val expectList = listOf(
            MainTodo(true, 1, "   강원용   "),
            MainTodo(true, 2, "깡원용"),
            MainTodo(true, 1, "ㄱ ㅏ ㅇ ㅜ ㅓ ㅇ ㅛㅇ   ")
        )
        // when
        val result: List<MainTodo> = searchUseCase("강  ", expectList).filterIsInstance<MainTodo>()
        // then
        assertThat(result).isEqualTo(listOf(MainTodo(true, 1, "   강원용   ")))
    }
}
