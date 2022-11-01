package hous.release.android.util.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.presentation.todo.add.ToDoUiState
import hous.release.android.util.theme.b2
import kotlinx.coroutines.Job

@Composable
fun TodoUserScreen(
    uiState: ToDoUiState,
    checkUser: (Int) -> Unit,
    selectTodoDay: (Int, Int) -> Unit,
    finish: () -> Boolean,
    name: String,
    putToDo: () -> Job
) {
    Surface(
        color = colorResource(id = R.color.white),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.to_do_manager),
                style = b2,
                color = colorResource(id = R.color.hous_black)
            )
            Spacer(modifier = Modifier.size(3.dp))
            ToDoUserProfiles(
                isEmptySelectedUser = uiState.selectedUsers.isEmpty(),
                selectedUsers = uiState.selectedUsers
            )
            Spacer(modifier = Modifier.size(20.dp))
            Divider(thickness = 1.dp, color = colorResource(id = R.color.hous_g_2))
            LazyColumn() {
                itemsIndexed(
                    items = uiState.todoUsers,
                    key = { _, user -> user.onBoardingId }
                ) { userIdx, user ->
                    ToDoUserItem(
                        userIdx = userIdx,
                        todoUser = user,
                        checkUser = checkUser
                    )
                    if (user.isChecked) {
                        DayItems(
                            userIdx = userIdx,
                            dayList = uiState.todoUsers[userIdx].dayOfWeeks,
                            selectTodoDay =
                            selectTodoDay
                        )
                    }
                    Divider(thickness = 1.dp, color = colorResource(id = R.color.hous_g_2))
                }
                item {
                    Spacer(modifier = Modifier.size(65.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize().padding(bottom = 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            ToDoButton(
                buttonState = getToDoButtonState(uiState),
                name = name,
                finish = finish,
                putToDo = putToDo,
                coroutineScope = rememberCoroutineScope()
            )
        }
    }
}

fun getToDoButtonState(uiState: ToDoUiState): ButtonState {
    if (uiState.isBlankTodoName) return ButtonState.INACTIVE
    if (uiState.selectedUsers.isEmpty()) return ButtonState.INACTIVE
    uiState.selectedUsers.forEach { toDoUser ->
        var hasCheckedDay = false
        toDoUser.dayOfWeeks.forEach { isChecked ->
            if (isChecked) {
                hasCheckedDay = true
                return@forEach
            }
        }
        if (!hasCheckedDay) return ButtonState.INACTIVE
    }
    return ButtonState.ACTIVE
}
