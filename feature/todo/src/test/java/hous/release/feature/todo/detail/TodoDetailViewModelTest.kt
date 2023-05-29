package hous.release.feature.todo.detail

import com.google.common.truth.Truth.assertThat
import hous.release.testing.CoroutinesTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class TodoDetailViewModelTest {
    private lateinit var todoDetailViewModel: TodoDetailViewModel

    @BeforeEach
    fun setUp() {
        todoDetailViewModel = TodoDetailViewModel()
    }

    @Test
    @DisplayName("요일을 복수로 선택할 수 있다.")
    fun dayTest() {
        assertAll(
            { assertThat(todoDetailViewModel.isSelectedDay.value[0]).isEqualTo(false) },
            { assertThat(todoDetailViewModel.isSelectedDay.value[1]).isEqualTo(false) },
            { assertThat(todoDetailViewModel.isSelectedDay.value[2]).isEqualTo(false) }
        )

        todoDetailViewModel.selectDay(0)
        todoDetailViewModel.selectDay(1)
        todoDetailViewModel.selectDay(2)

        assertAll(
            { assertThat(todoDetailViewModel.isSelectedDay.value[0]).isEqualTo(true) },
            { assertThat(todoDetailViewModel.isSelectedDay.value[1]).isEqualTo(true) },
            { assertThat(todoDetailViewModel.isSelectedDay.value[2]).isEqualTo(true) }
        )
    }
}