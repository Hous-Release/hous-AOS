package hous.release.feature.todo.detail

import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.todo.Homy
import hous.release.domain.usecase.todo.GetHomiesUseCase
import hous.release.testing.CoroutinesTestExtension
import hous.release.testing.callPrivateFunc
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class TodoDetailViewModelTest {
    private lateinit var todoDetailViewModel: TodoDetailViewModel
    private val getHomiesUseCase: GetHomiesUseCase = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        todoDetailViewModel = TodoDetailViewModel(getHomiesUseCase)
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

    @Test
    @DisplayName("SelectableHomy 타입으로 매핑한다.")
    fun homyChipClickTest() = runTest {
        coEvery { getHomiesUseCase() } returns listOf(
            Homy(0, "KWY", HomyType.BLUE),
            Homy(1, "SYJ", HomyType.BLUE),
            Homy(2, "LJW", HomyType.BLUE),
            Homy(3, "LYJ", HomyType.BLUE),
        )

        todoDetailViewModel.callPrivateFunc("getHomies")

        assertAll(
            { assertIs<SelectableHomy>(todoDetailViewModel.homies.value[0]) },
            { assertIs<SelectableHomy>(todoDetailViewModel.homies.value[1]) },
            { assertIs<SelectableHomy>(todoDetailViewModel.homies.value[2]) },
            { assertIs<SelectableHomy>(todoDetailViewModel.homies.value[3]) }
        )
    }

    @Test
    @DisplayName("호미를 복수로 클릭할 수 있다.")
    fun homyChipClickTest2() = runTest {
        coEvery { getHomiesUseCase() } returns listOf(
            Homy(0, "KWY", HomyType.BLUE),
            Homy(1, "SYJ", HomyType.BLUE),
            Homy(2, "LJW", HomyType.BLUE),
            Homy(3, "LYJ", HomyType.BLUE),
        )
        todoDetailViewModel.callPrivateFunc("getHomies")

        assertAll(
            { assertThat(todoDetailViewModel.homies.value[0].isSelected).isEqualTo(false) },
            { assertThat(todoDetailViewModel.homies.value[1].isSelected).isEqualTo(false) },
            { assertThat(todoDetailViewModel.homies.value[2].isSelected).isEqualTo(false) },
            { assertThat(todoDetailViewModel.homies.value[3].isSelected).isEqualTo(false) }
        )

        todoDetailViewModel.selectHomy(0)
        todoDetailViewModel.selectHomy(1)
        todoDetailViewModel.selectHomy(2)

        assertAll(
            { assertThat(todoDetailViewModel.homies.value[0].isSelected).isEqualTo(true) },
            { assertThat(todoDetailViewModel.homies.value[1].isSelected).isEqualTo(true) },
            { assertThat(todoDetailViewModel.homies.value[2].isSelected).isEqualTo(true) },
            { assertThat(todoDetailViewModel.homies.value[3].isSelected).isEqualTo(false) }
        )
    }
}
