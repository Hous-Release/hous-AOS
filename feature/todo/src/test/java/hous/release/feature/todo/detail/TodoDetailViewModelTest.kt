package hous.release.feature.todo.detail

import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.todo.Homy
import hous.release.domain.usecase.todo.GetFilteredTodoUseCase
import hous.release.domain.usecase.todo.GetHomiesUseCase
import hous.release.testing.CoroutinesTestExtension
import hous.release.testing.callPrivateFunc
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class TodoDetailViewModelTest {
    private lateinit var todoDetailViewModel: TodoDetailViewModel
    private val getHomiesUseCase: GetHomiesUseCase = mockk(relaxed = true)
    private val getFilteredTodoUseCase: GetFilteredTodoUseCase = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        todoDetailViewModel = TodoDetailViewModel(getHomiesUseCase, getFilteredTodoUseCase)
    }

    @Nested
    @DisplayName("week 관련 테스트 모음")
    inner class WeekRelatedTest {
        @Test
        @DisplayName("setDay 함수는 String 을 selectableWeek 객체로 매핑 후 저장한다.")
        fun setDayTest() {
            // given
            val result = listOf(
                SelectableDayOfWeek(dayOfWeek = "월"),
                SelectableDayOfWeek(dayOfWeek = "화"),
                SelectableDayOfWeek(dayOfWeek = "수"),
                SelectableDayOfWeek(dayOfWeek = "목"),
                SelectableDayOfWeek(dayOfWeek = "금"),
                SelectableDayOfWeek(dayOfWeek = "토"),
                SelectableDayOfWeek(dayOfWeek = "일")
            )

            // when
            todoDetailViewModel.callPrivateFunc("setWeek")

            // then
            assertThat(todoDetailViewModel.week.value).isEqualTo(result)
        }

        @Test
        @DisplayName("selectDay 함수는 해당 요일을 반전한다.")
        fun dayTest() {
            // given
            todoDetailViewModel.callPrivateFunc("setWeek")

            // when
            todoDetailViewModel.selectDay(0)
            todoDetailViewModel.selectDay(1)
            todoDetailViewModel.selectDay(2)

            // then
            assertAll(
                { assertThat(todoDetailViewModel.week.value[0].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.week.value[1].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.week.value[2].isSelected).isEqualTo(true) }
            )
        }

        @Test
        @DisplayName("selectDay 클릭 시 해당 인덱스에 해당하는 요일들이 string으로 표시된다.")
        fun filterWeekTest() = runTest {
            // given
            todoDetailViewModel.callPrivateFunc("setWeek")
            val collectJob =
                launch(UnconfinedTestDispatcher()) { todoDetailViewModel.selectedDays.collect() }

            // 월(0) 화(1) 수(2) 목(3) 금(4) 토(5) 일(6)
            // when
            todoDetailViewModel.selectDay(0)
            todoDetailViewModel.selectDay(4)
            todoDetailViewModel.selectDay(2)

            // then
            assertThat(todoDetailViewModel.selectedDays.value).isEqualTo("월, 수, 금")

            collectJob.cancel()
        }
    }

    @Nested
    @DisplayName("homies 관련 테스트 모음")
    inner class HomiesRelatedTest {
        @Test
        @DisplayName("setHomies 함수는 Homy 객체를 SelectableHomy 객체로 매핑 후 저장한다.")
        fun homyChipClickTest() = runTest {
            // given
            coEvery { getHomiesUseCase() } returns listOf(
                Homy(0, "KWY", HomyType.BLUE),
                Homy(1, "SYJ", HomyType.BLUE),
                Homy(2, "LJW", HomyType.BLUE),
                Homy(3, "LYJ", HomyType.BLUE),
            )

            // when
            todoDetailViewModel.callPrivateFunc("setHomies")

            // then
            assertThat(todoDetailViewModel.homies.value).isEqualTo(
                listOf(
                    SelectableHomy(0, name = "KWY", homyType = HomyType.BLUE),
                    SelectableHomy(1, name = "SYJ", homyType = HomyType.BLUE),
                    SelectableHomy(2, name = "LJW", homyType = HomyType.BLUE),
                    SelectableHomy(3, name = "LYJ", homyType = HomyType.BLUE),
                )
            )
        }

        @Test
        @DisplayName("selectHomy 함수는 호미의 selected 를 반전한다.")
        fun homyChipClickTest2() = runTest {
            // given
            coEvery { getHomiesUseCase() } returns listOf(
                Homy(0, "KWY", HomyType.BLUE),
                Homy(1, "SYJ", HomyType.BLUE),
                Homy(2, "LJW", HomyType.BLUE),
                Homy(3, "LYJ", HomyType.BLUE),
            )
            todoDetailViewModel.callPrivateFunc("setHomies")

            // when
            todoDetailViewModel.selectHomy(0)
            todoDetailViewModel.selectHomy(1)
            todoDetailViewModel.selectHomy(2)

            // then
            assertAll(
                { assertThat(todoDetailViewModel.homies.value[0].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.homies.value[1].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.homies.value[2].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.homies.value[3].isSelected).isEqualTo(false) }
            )
        }

        @Test
        @DisplayName("클릭한 homy가 한명일 때 해당 homy의 이름을 표출한다.")
        fun filterHomiesNameTest() = runTest {
            // given
            coEvery { getHomiesUseCase() } returns listOf(
                Homy(0, "KWY", HomyType.BLUE),
                Homy(1, "SYJ", HomyType.BLUE),
                Homy(2, "LJW", HomyType.BLUE),
                Homy(3, "LYJ", HomyType.BLUE),
            )
            val collectJob =
                launch(UnconfinedTestDispatcher()) { todoDetailViewModel.selectedHomies.collect() }
            todoDetailViewModel.callPrivateFunc("setHomies")

            // when
            todoDetailViewModel.selectHomy(0)

            // then
            assertThat(todoDetailViewModel.selectedHomies.value).isEqualTo("KWY")

            collectJob.cancel()
        }

        @Test
        @DisplayName("클릭한 homy가 두명 이상일 때 `{호미 이름} 외 {선택한 호미의 수 -1}` 형식으로 이름을 표시한다.")
        fun filterHomiesNameTest2() = runTest {
            // given
            coEvery { getHomiesUseCase() } returns listOf(
                Homy(0, "KWY", HomyType.BLUE),
                Homy(1, "SYJ", HomyType.BLUE),
                Homy(2, "LJW", HomyType.BLUE),
                Homy(3, "LYJ", HomyType.BLUE),
            )
            val collectJob =
                launch(UnconfinedTestDispatcher()) { todoDetailViewModel.selectedHomies.collect() }
            todoDetailViewModel.callPrivateFunc("setHomies")

            // when
            todoDetailViewModel.selectHomy(0)
            todoDetailViewModel.selectHomy(1)
            todoDetailViewModel.selectHomy(2)

            // then
            assertThat(todoDetailViewModel.selectedHomies.value).isEqualTo("KWY 외 2명")

            collectJob.cancel()
        }
    }
}
