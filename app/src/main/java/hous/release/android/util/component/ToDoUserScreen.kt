package hous.release.android.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.designsystem.component.HousLeftLimitedTextField
import hous.release.designsystem.component.HousToolbarSlot
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG1
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun TodoUserScreen(
    title: String,
    todoText: String,
    buttonText: String,
    uiState: UpdateToDoUiState,
    setTodoText: (String) -> Unit,
    checkUser: (Int) -> Unit,
    selectTodoDay: (Int, Int) -> Unit,
    checkFinish: () -> Unit,
    showLoadingDialog: () -> Unit,
    putToDo: () -> Job,
    hideKeyBoard: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                hideKeyBoard()
            }
    ) {
        HousToolbarSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 24.dp, end = 8.dp),
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        checkFinish()
                    },
                    painter = painterResource(id = hous.release.designsystem.R.drawable.ic_back),
                    contentDescription = null
                )
            },
            title = title,
            trailingIcon = {
                Text(
                    modifier = Modifier
                        .clickable(uiState.buttonState == ButtonState.ACTIVE) {
                            coroutineScope.launch {
                                showLoadingDialog()
                                putToDo()
                            }
                        },
                    text = buttonText,
                    style = HousTheme.typography.b2,
                    color = if (uiState.buttonState == ButtonState.ACTIVE) HousBlue else HousBlueL1
                )
            }
        )
        TodoTitleGuide()
        Spacer(modifier = Modifier.height(4.dp))
        HousLeftLimitedTextField(
            modifier = Modifier,
            text = todoText,
            onTextChange = setTodoText,
            color = HousG1,
            limitTextCount = 10
        )
        Spacer(modifier = Modifier.height(8.dp))
        TodoNotificationCheckBox(uiState.isPushNotification)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.to_do_manager),
            style = HousTheme.typography.b2,
            color = colorResource(id = R.color.hous_black)
        )
        if (uiState.selectedUsers.isNotEmpty()) {
            Spacer(modifier = Modifier.size(8.dp))
            ToDoUserProfiles(
                selectedUsers = uiState.selectedUsers
            )
            Spacer(modifier = Modifier.size(20.dp))
        } else {
            Spacer(modifier = Modifier.size(16.dp))
        }
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
}

@Composable
private fun TodoNotificationCheckBox(
    isCheck: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (isCheck) R.drawable.ic_check_on else R.drawable.ic_check_off),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.todo_notification),
            color = if (isCheck) HousBlue else HousG4,
            style = HousTheme.typography.b3
        )
    }
}

@Composable
private fun TodoTitleGuide() {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.b3.fontFamily,
                    fontWeight = HousTheme.typography.b3.fontWeight,
                    fontSize = HousTheme.typography.b3.fontSize,
                    color = HousBlack
                )
            ) {
                append(stringResource(R.string.todo_add_edit_title))
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.b3.fontFamily,
                    fontWeight = HousTheme.typography.b3.fontWeight,
                    fontSize = HousTheme.typography.b3.fontSize,
                    color = HousRed
                )
            ) {
                append(stringResource(R.string.todo_star))
            }
        }
    )
}
