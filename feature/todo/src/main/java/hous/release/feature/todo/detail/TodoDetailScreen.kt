@file:OptIn(ExperimentalMaterialApi::class)

package hous.release.feature.todo.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hous.release.designsystem.component.FabScreenSlot
import hous.release.designsystem.component.HousDialog
import hous.release.designsystem.component.HousSearchTextField
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.todo.TodoWithNew
import hous.release.feature.todo.R
import hous.release.feature.todo.detail.component.FilterBottomSheet
import hous.release.feature.todo.detail.component.SearchResultText
import hous.release.feature.todo.detail.component.ToDoItem
import hous.release.feature.todo.detail.component.TodoDetailBottomSheet
import hous.release.feature.todo.detail.component.TodoDetailToolbar
import hous.release.feature.todo.detail.component.TodoFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val FILTER_BOTTOM_SHEET = 0
const val DETAIL_BOTTOM_SHEET = 1

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TodoDetailScreen(
    todoDetailViewModel: TodoDetailViewModel,
    finish: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val searchText = todoDetailViewModel.searchText.collectAsStateWithLifecycle()
    val selectedDayOfWeek = todoDetailViewModel.selectedDayOfWeeks.collectAsStateWithLifecycle()
    val selectedHomies = todoDetailViewModel.selectedHomies.collectAsStateWithLifecycle()
    val filteredTodo = todoDetailViewModel.filteredTodo.collectAsStateWithLifecycle()
    val homies = todoDetailViewModel.homies.collectAsStateWithLifecycle()
    val selectableWeek = todoDetailViewModel.selectableWeek.collectAsStateWithLifecycle()
    val todoDetail = todoDetailViewModel.todoDetail.collectAsStateWithLifecycle()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val (selected, setSelected) = remember(calculation = { mutableStateOf(FILTER_BOTTOM_SHEET) })
    var isDeleteTodo by remember { mutableStateOf(false) }

    if (isDeleteTodo) {
        HousDialog(
            title = stringResource(R.string.todo_delete_title),
            content = stringResource(R.string.todo_delete_content),
            neutralText = stringResource(R.string.todo_delete_cancel),
            actionText = stringResource(R.string.todo_delete_remove),
            actionOnClick = {
                coroutineScope.launch {
                    todoDetailViewModel.deleteTodo()
                    isDeleteTodo = false
                    bottomSheetState.hide()
                }
            },
            onDismissRequest = { isDeleteTodo = false })
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetContent(
                selected = selected,
                homies = homies.value,
                selectableWeek = selectableWeek.value,
                todoDetail = todoDetail.value,
                getTodosAppliedFilter = {
                    coroutineScope.launch {
                        /* TODO 필터링 api 연결 */
                        bottomSheetState.hide()
                    }
                },
                selectDayOfWeek = todoDetailViewModel::selectDayOfWeek,
                selectHomy = todoDetailViewModel::selectHomy,
                editAction = {
                    coroutineScope.launch {
                        /* TODO edit 화면으로 이동 */
                        bottomSheetState.hide()
                    }
                },
                deleteAction = { isDeleteTodo = true }
            )
        }
    ) {
        FabScreenSlot(
            fabOnClick = {
                /* Todo 추가하기 뷰로 이동 */
            }
        ) {
            TodoDetailContent(
                searchText = searchText.value,
                selectedDayOfWeek = selectedDayOfWeek.value,
                selectedHomies = selectedHomies.value,
                todos = filteredTodo.value.todos,
                writeSearchText = todoDetailViewModel::writeSearchText,
                showFilterBottomSheet = {
                    coroutineScope.launch {
                        setSelected(FILTER_BOTTOM_SHEET)
                        bottomSheetState.show()
                    }
                },
                showToDoDetailBottomSheet = { todoId ->
                    coroutineScope.launch {
                        todoDetailViewModel.setTodoDetail(todoId)
                        setSelected(DETAIL_BOTTOM_SHEET)
                        bottomSheetState.show()
                    }
                },
                finish = finish
            )
        }
    }
}

@Composable
private fun TodoDetailContent(
    searchText: String,
    selectedDayOfWeek: String,
    selectedHomies: String,
    todos: List<TodoWithNew>,
    writeSearchText: (String) -> Unit,
    showFilterBottomSheet: () -> Unit,
    showToDoDetailBottomSheet: (Int) -> Unit,
    finish: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TodoDetailToolbar(finish = finish)
        Spacer(modifier = Modifier.height(4.dp))
        HousSearchTextField(
            text = searchText,
            hint = stringResource(R.string.todo_detail_textfield_hint),
            onTextChange = writeSearchText
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodoFilterAndSearchResult(
            selectedDayOfWeek = selectedDayOfWeek,
            selectedHomies = selectedHomies,
            searchText = searchText,
            filteredTodoCnt = todos.size,
            showFilterBottomSheet = showFilterBottomSheet
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (todos.isNotEmpty()) {
            Todos(
                todos = todos,
                showToDoDetailBottomSheet = showToDoDetailBottomSheet
            )
        }
    }
    if (todos.isEmpty()) {
        EmptyGuideText(searchText)
    }
}

@Composable
private fun Todos(
    todos: List<TodoWithNew>,
    showToDoDetailBottomSheet: (Int) -> Unit
) {
    LazyColumn {
        items(
            items = todos,
            key = { todo ->
                todo.id
            }
        ) { todo ->
            ToDoItem(
                todo = todo,
                showToDoDetailBottomSheet = showToDoDetailBottomSheet
            )
        }
    }
}

@Composable
private fun TodoFilterAndSearchResult(
    selectedDayOfWeek: String,
    selectedHomies: String,
    searchText: String,
    filteredTodoCnt: Int,
    showFilterBottomSheet: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TodoFilter(
            selectedDayOfWeeks = selectedDayOfWeek,
            selectedHomies = selectedHomies,
            isShowBottomSheet = false,
            showFilterBottomSheet = showFilterBottomSheet
        )
        if (searchText.isNotBlank()) {
            SearchResultText(searchResultCnt = filteredTodoCnt)
        }
    }
}

@Composable
private fun EmptyGuideText(
    searchText: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (searchText.isBlank()) stringResource(R.string.todo_detail_empty)
            else stringResource(R.string.todo_filter_empty),
            style = HousTheme.typography.b2,
            color = HousG5
        )
    }
}

@Composable
private fun BottomSheetContent(
    selected: Int,
    homies: List<SelectableHomy>,
    selectableWeek: List<SelectableDayOfWeek>,
    todoDetail: TodoDetail,
    getTodosAppliedFilter: () -> Unit,
    selectDayOfWeek: (Int) -> Unit,
    selectHomy: (Int) -> Unit,
    editAction: (Int) -> Unit,
    deleteAction: (Int) -> Unit
) {
    when (selected) {
        FILTER_BOTTOM_SHEET -> {
            FilterBottomSheet(
                homies = homies,
                selectableWeek = selectableWeek,
                selectDayOfWeek = selectDayOfWeek,
                selectHomy = selectHomy,
                getTodosAppliedFilter = getTodosAppliedFilter
            )
        }
        DETAIL_BOTTOM_SHEET -> {
            TodoDetailBottomSheet(
                todoDetail = todoDetail,
                editAction = editAction,
                deleteAction = deleteAction
            )
        }
    }
}
