package hous.release.data.entity.request.todo

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class FilteredTodoRequestTest {
    @Test
    @DisplayName("월, 화, 토.. 형식의 string을 MONDAY, TUESDAY, SATURDAY 형식으로 변환한다.")
    fun instanceOfTest() {
        // given
        val expect = FilteredTodoRequest(listOf("MONDAY", "TUESDAY", "SATURDAY"), null)

        // when
        val result = FilteredTodoRequest.instanceOf(listOf("월", "화", "토"), null)

        // then
        assertThat(result).isEqualTo(expect)
    }

    @Test
    @DisplayName("파라미터로 모두 null이 들어왔을 때, 데이터 값에 null 을 그대로 넣는다.")
    fun instanceOfTest2() {
        // given
        val expect = FilteredTodoRequest(null, null)

        // when
        val result = FilteredTodoRequest.instanceOf(null, null)

        // then
        assertThat(result).isEqualTo(expect)
    }
}
