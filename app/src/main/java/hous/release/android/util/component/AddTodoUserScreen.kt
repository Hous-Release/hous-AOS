package hous.release.android.util.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.todo.add.AddToDoVIewModel
import hous.release.android.util.extension.collectAsStateWithLifecycleRemember
import hous.release.android.util.theme.b2

@Composable
fun AddTodoUserScreen(
    viewModel: AddToDoVIewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycleRemember(AddToDoVIewModel.AddToDoUiState())
    Surface(color = colorResource(id = R.color.white)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "담당자",
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(
                    items = uiState.todoUsers,
                    key = { _, user -> user.onBoardingId }
                ) { userIdx, user ->
                    ToDoUserItem(
                        userIdx = userIdx,
                        todoUser = user,
                        checkUser = viewModel::checkUser
                    )
                    if (user.isChecked) {
                        DayItems(
                            userIdx = userIdx,
                            dayList = uiState.todoUsers[userIdx].dayOfWeeks,
                            selectTodoDay =
                            viewModel::selectTodoDay
                        )
                    }
                    Divider(thickness = 1.dp, color = colorResource(id = R.color.hous_g_2))
                }
            }
        }
    }
}
