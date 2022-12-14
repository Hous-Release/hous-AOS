package hous.release.android.util.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.util.style.HousTheme
import kotlinx.coroutines.Job

@Composable
fun TodoUserScreen(
    uiState: UpdateToDoUiState,
    checkUser: (Int) -> Unit,
    selectTodoDay: (Int, Int) -> Unit,
    showLoadingDialog: () -> Unit,
    name: String,
    putToDo: () -> Job,
    hideKeyBoard: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        color = colorResource(id = R.color.white),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 83.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    hideKeyBoard()
                }
        ) {
            Text(
                text = stringResource(id = R.string.to_do_manager),
                style = HousTheme.typography.b2,
                color = colorResource(id = R.color.hous_black)
            )
            Spacer(modifier = Modifier.size(3.dp))
            if (uiState.selectedUsers.isNotEmpty()) {
                Spacer(modifier = Modifier.size(11.dp))
                ToDoUserProfiles(
                    selectedUsers = uiState.selectedUsers
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Divider(thickness = 1.dp, color = colorResource(id = R.color.hous_g_2))
            LazyColumn {
                itemsIndexed(
                    items = uiState.todoUsers,
                    key = { _, user -> user.onBoardingId }
                ) { userIdx, user ->
                    ToDoUserItem(
                        userIdx = userIdx,
                        todoUser = user,
                        checkUser = checkUser,
                        hideKeyBoard = hideKeyBoard
                    )
                    if (user.isChecked) {
                        DayItems(
                            userIdx = userIdx,
                            dayList = uiState.todoUsers[userIdx].dayOfWeeks,
                            selectTodoDay = selectTodoDay,
                            hideKeyBoard = hideKeyBoard
                        )
                    }
                    Divider(thickness = 1.dp, color = colorResource(id = R.color.hous_g_2))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            ToDoButton(
                buttonState = uiState.buttonState,
                name = name,
                showLoadingDialog = showLoadingDialog,
                putToDo = putToDo,
                coroutineScope = rememberCoroutineScope()
            )
        }
    }
}
