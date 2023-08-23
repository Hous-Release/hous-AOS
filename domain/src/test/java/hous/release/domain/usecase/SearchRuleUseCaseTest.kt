package hous.release.domain.usecase

import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.BaseRule
import hous.release.domain.usecase.search.SearchRuleUseCase
import hous.release.domain.usecase.search.matcher.RuleNameMatcher
import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class SearchRuleUseCaseTest {

    data class MainTodo(
        val isNew: Boolean = false,
        override val id: Int = 0,
        override val name: String = ""
    ) : BaseRule(id, name)

    @Test
    fun `rule의 name에 검색값이 매칭되는 rule을 반환한다`() {
        // given
        val searchUseCase = SearchRuleUseCase(RuleNameMatcher(MixedEnKrMatchStrategy()))

        val rules: List<BaseRule> =
            listOf(
                MainTodo(name = "이준원"),
                MainTodo(name = " 이준원MURJUNE "),
                MainTodo(name = "이준원murjune"),
                MainTodo(name = "이#준@원")
            )
        // when
        val res: List<BaseRule> = searchUseCase("ㄱㄱ", rules)
        val res2: List<BaseRule> = searchUseCase("ㅇㅈㅇ", rules)
        val res3: List<BaseRule> = searchUseCase("muㅕrj&une", rules)
        // then
        assertAll(
            { assertThat(res).isEqualTo(emptyList<BaseRule>()) },
            { assertThat(res2).isEqualTo(rules) },
            {
                assertThat(res3).isEqualTo(
                    listOf(
                        MainTodo(name = " 이준원MURJUNE "),
                        MainTodo(name = "이준원murjune")
                    )
                )
            }
        )
    }
}
