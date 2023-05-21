package hous.release.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import javax.inject.Inject

internal class ExampleUseCaseTest {
    abstract class Rule(
        open val id: Int,
        open val name: String
    )

    data class MainTodo(
        val isNew: Boolean,
        override val id: Int,
        override val name: String
    ) : Rule(id, name)

    class ExampleUseCase @Inject constructor() {
        operator fun invoke(search: String, rules: List<Rule>): List<Rule> {
            return rules
        }
    }

    private val searchUseCase = ExampleUseCase()

    @Test
    @DisplayName("rule 반환")
    fun useCaseTest() {
        val expectList =
            listOf(MainTodo(true, 1, "KWY"), MainTodo(true, 1, "KWY"), MainTodo(true, 1, "LJW"))

        val result: List<MainTodo> = searchUseCase("srar", expectList).filterIsInstance<MainTodo>()

        expectList.forEachIndexed { i, d ->
            assertThat(d).isEqualTo(result[i])
        }
    }
}
