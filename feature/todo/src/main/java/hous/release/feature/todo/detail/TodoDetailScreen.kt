package hous.release.feature.todo.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hous.release.designsystem.component.FabScreenSlot
import hous.release.designsystem.component.HousSearchTextField
import hous.release.feature.todo.R
import hous.release.feature.todo.detail.component.FilterBottomSheet
import hous.release.feature.todo.detail.component.SearchResultText
import hous.release.feature.todo.detail.component.ToDoItem
import hous.release.feature.todo.detail.component.TodoDetailToolbar
import hous.release.feature.todo.detail.component.TodoFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoDetailScreen(
    todoDetailViewModel: TodoDetailViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val searchText = todoDetailViewModel.searchText.collectAsStateWithLifecycle()
    val selectedDayOfWeek = todoDetailViewModel.selectedDayOfWeeks.collectAsStateWithLifecycle()
    val selectedHomies = todoDetailViewModel.selectedHomies.collectAsStateWithLifecycle()
    val filteredTodo = todoDetailViewModel.filteredTodo.collectAsStateWithLifecycle()
    val homies = todoDetailViewModel.homies.collectAsStateWithLifecycle()
    val week = todoDetailViewModel.week.collectAsStateWithLifecycle()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(sheetState = bottomSheetState, sheetContent = {
        FilterBottomSheet(
            homies = homies.value,
            isSelectedDay = week.value,
            selectTodoDay = todoDetailViewModel::selectDayOfWeek,
            selectHomy = todoDetailViewModel::selectHomy,
            getTodosAppliedFilter = { /*TODO*/ }
        )
    }) {
        FabScreenSlot(
            fabOnClick = {
                /* Todo 추가하기 뷰로 이동 */
            }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                TodoDetailToolbar(
                    finish = {}
                )
                Spacer(modifier = Modifier.height(4.dp))
                HousSearchTextField(
                    text = searchText.value,
                    hint = stringResource(R.string.todo_detail_textfield_hint),
                    onTextChange = todoDetailViewModel::writeSearchText
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TodoFilter(
                        selectedDayOfWeek = selectedDayOfWeek.value,
                        selectedHomies = selectedHomies.value,
                        isShowBottomSheet = false,
                        showFilterBottomSheet = {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }
                    )
                    if (searchText.value.isNotBlank()) {
                        SearchResultText(searchResultCnt = filteredTodo.value.todos.size)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn {
                    items(filteredTodo.value.todos) { todo ->
                        ToDoItem(
                            todo = todo,
                            showToDoDetailBottomSheet = { /* todo detail bottom sheet */ }
                        )
                    }
                }
            }
        }
    }
}
