package hous.release.domain.usecase


import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.MainTodo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SearchUseCaseTest {
    private val searchUseCase = SearchUseCase()

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