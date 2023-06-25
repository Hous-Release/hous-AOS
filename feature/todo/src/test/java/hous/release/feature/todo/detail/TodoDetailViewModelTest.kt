package hous.release.feature.todo.detail

import com.google.common.truth.Truth.assertThat
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.todo.FilteredTodo
import hous.release.domain.entity.todo.Homy
import hous.release.domain.entity.todo.TodoWithNew
import hous.release.domain.repository.TodoRepository
import hous.release.domain.usecase.search.SearchRuleUseCase
import hous.release.domain.usecase.search.matcher.RuleNameMatcher
import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import hous.release.domain.usecase.todo.GetFilteredTodoUseCase
import hous.release.domain.usecase.todo.GetHomiesUseCase
import hous.release.domain.usecase.todo.GetIsAddableTodoUseCase
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
    private lateinit var getFilteredTodoUseCase: GetFilteredTodoUseCase
    private lateinit var searchRuleUseCase: SearchRuleUseCase
    private lateinit var getIsAddableTodoUseCase: GetIsAddableTodoUseCase
    private val todoRepository: TodoRepository = mockk()
    private val getHomiesUseCase: GetHomiesUseCase = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        getFilteredTodoUseCase = GetFilteredTodoUseCase(todoRepository)
        searchRuleUseCase = SearchRuleUseCase(RuleNameMatcher(MixedEnKrMatchStrategy()))
        getIsAddableTodoUseCase = GetIsAddableTodoUseCase(todoRepository)
        todoDetailViewModel =
            TodoDetailViewModel(
                getHomiesUseCase,
                getFilteredTodoUseCase,
                searchRuleUseCase,
                getIsAddableTodoUseCase
            )
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
            todoDetailViewModel.callPrivateFunc("setSelectableWeek")

            // then
            assertThat(todoDetailViewModel.selectableWeek.value).isEqualTo(result)
        }

        @Test
        @DisplayName("selectDay 함수는 해당 요일을 반전한다.")
        fun dayTest() {
            // given
            todoDetailViewModel.callPrivateFunc("setSelectableWeek")

            // when
            todoDetailViewModel.selectDayOfWeek(0)
            todoDetailViewModel.selectDayOfWeek(1)
            todoDetailViewModel.selectDayOfWeek(2)

            // then
            assertAll(
                { assertThat(todoDetailViewModel.selectableWeek.value[0].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.selectableWeek.value[1].isSelected).isEqualTo(true) },
                { assertThat(todoDetailViewModel.selectableWeek.value[2].isSelected).isEqualTo(true) }
            )
        }

        @Test
        @DisplayName("selectDay 클릭 시 해당 인덱스에 해당하는 요일들이 string으로 표시된다.")
        fun filterWeekTest() = runTest {
            // given
            todoDetailViewModel.callPrivateFunc("setSelectableWeek")
            val collectJob =
                launch(UnconfinedTestDispatcher()) { todoDetailViewModel.selectedDayOfWeeks.collect() }

            // 월(0) 화(1) 수(2) 목(3) 금(4) 토(5) 일(6)
            // when
            todoDetailViewModel.selectDayOfWeek(0)
            todoDetailViewModel.selectDayOfWeek(4)
            todoDetailViewModel.selectDayOfWeek(2)

            // then
            assertThat(todoDetailViewModel.selectedDayOfWeeks.value).isEqualTo("월, 수, 금")

            collectJob.cancel()
        }

        @Test
        @DisplayName("selectDay 모두 클릭 했을 때 '매일' 로 표시한다.")
        fun filterWeekTest2() = runTest {
            // given
            todoDetailViewModel.callPrivateFunc("setSelectableWeek")
            val collectJob =
                launch(UnconfinedTestDispatcher()) { todoDetailViewModel.selectedDayOfWeeks.collect() }

            // 월(0) 화(1) 수(2) 목(3) 금(4) 토(5) 일(6)
            // when
            todoDetailViewModel.selectDayOfWeek(0)
            todoDetailViewModel.selectDayOfWeek(1)
            todoDetailViewModel.selectDayOfWeek(2)
            todoDetailViewModel.selectDayOfWeek(3)
            todoDetailViewModel.selectDayOfWeek(4)
            todoDetailViewModel.selectDayOfWeek(5)
            todoDetailViewModel.selectDayOfWeek(6)

            // then
            assertThat(todoDetailViewModel.selectedDayOfWeeks.value).isEqualTo("매일")

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
                Homy(3, "LYJ", HomyType.BLUE)
            )

            // when
            todoDetailViewModel.callPrivateFunc("setHomies")

            // then
            assertThat(todoDetailViewModel.homies.value).isEqualTo(
                listOf(
                    SelectableHomy(0, name = "KWY", homyType = HomyType.BLUE),
                    SelectableHomy(1, name = "SYJ", homyType = HomyType.BLUE),
                    SelectableHomy(2, name = "LJW", homyType = HomyType.BLUE),
                    SelectableHomy(3, name = "LYJ", homyType = HomyType.BLUE)
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
                Homy(3, "LYJ", HomyType.BLUE)
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
                Homy(3, "LYJ", HomyType.BLUE)
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
                Homy(3, "LYJ", HomyType.BLUE)
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

    @Nested
    @DisplayName("todo 관련 테스트 모음")
    inner class FilteredTodoTest {
        @Test
        @DisplayName("setFilteredTodo 함수를 호출하면 필터링된 FilteredTodo 객체를 적용한다.")
        fun setFilteredTodoTest() = runTest {
            val expectedValue = listOf(
                TodoWithNew(
                    id = 1,
                    name = "todo1",
                    isNew = false
                ),
                TodoWithNew(
                    id = 2,
                    name = "todo2",
                    isNew = false
                ),
                TodoWithNew(
                    id = 3,
                    name = "todo3",
                    isNew = false
                )
            )
            // given
            coEvery { todoRepository.getFilteredTodos(null, null) } returns Result.success(
                FilteredTodo(
                    todos = expectedValue,
                    todosCnt = expectedValue.size
                )
            )

            // when
            todoDetailViewModel.fetchFilteredTodo()

            // then
            assertThat(todoDetailViewModel.filteredTodo.value.todos).isEqualTo(expectedValue)
            assertThat(todoDetailViewModel.filteredTodo.value.todosCnt).isEqualTo(expectedValue.size)
        }

        @Test
        @DisplayName("writeSearchText 함수를 통해 text를 입력하면 해당 text를 포함한 todo를 검색한다.")
        fun setSearchRuleTest() = runTest {
            // given
            val expectedValue = listOf(
                TodoWithNew(
                    id = 1,
                    name = "todo1",
                    isNew = false
                ),
                TodoWithNew(
                    id = 2,
                    name = "todo2",
                    isNew = false
                ),
                TodoWithNew(
                    id = 3,
                    name = "todo3",
                    isNew = false
                )
            )
            coEvery { todoRepository.getFilteredTodos(null, null) } returns Result.success(
                FilteredTodo(
                    todos = expectedValue,
                    todosCnt = expectedValue.size
                )
            )
            todoDetailViewModel.fetchFilteredTodo()

            // when
            todoDetailViewModel.writeSearchText("1")

            // then
            assertThat(todoDetailViewModel.filteredTodo.value.todos).isEqualTo(listOf(expectedValue[0]))
        }

        @Test
        @DisplayName("검색 기능과 필터 기능을 동시 사용했을 경우 교집합으로 처리한다.")
        fun setFilteredTodoTest2() = runTest {
            // given
            val expectedValue = listOf(
                TodoWithNew(
                    id = 1,
                    name = "todo1",
                    isNew = false
                ),
                TodoWithNew(
                    id = 2,
                    name = "todo2",
                    isNew = false
                ),
                TodoWithNew(
                    id = 3,
                    name = "todo3",
                    isNew = false
                )
            )
            coEvery { todoRepository.getFilteredTodos(null, null) } returns Result.success(
                FilteredTodo(
                    todos = expectedValue,
                    todosCnt = expectedValue.size
                )
            )
            todoDetailViewModel.writeSearchText("1")

            // when
            todoDetailViewModel.fetchFilteredTodo()

            // then
            assertThat(todoDetailViewModel.filteredTodo.value.todos).isEqualTo(listOf(expectedValue[0]))
            assertThat(todoDetailViewModel.filteredTodo.value.todosCnt).isEqualTo(expectedValue.size)
        }
    }
}
