package hous.release.feature.todo.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.HomyChip
import hous.release.designsystem.component.HousDetailBottomSheet
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.TodoDetail.User

@Composable
fun TodoDetailBottomSheet(
    todoDetail: TodoDetail,
    editAction: (Int) -> Unit,
    deleteAction: (Int) -> Unit
) {
    HousDetailBottomSheet(
        todoId = todoDetail.todoId,
        content = {
            TodoDetailBottomSheetContent(
                todo = todoDetail.name,
                dayOfWeeks = todoDetail.dayOfWeeks,
                homies = todoDetail.selectedUsers
            )
        },
        editAction = editAction,
        deleteAction = deleteAction
    )
}

@Composable
private fun TodoDetailBottomSheetContent(
    todo: String,
    dayOfWeeks: String,
    homies: List<User>
) {
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(
            text = todo,
            style = HousTheme.typography.b1,
            color = HousBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dayOfWeeks,
            style = HousTheme.typography.b3,
            color = HousG6
        )
        Spacer(modifier = Modifier.height(12.dp))
        SelectedHomies(homies)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun SelectedHomies(
    homies: List<User>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = homies,
            key = { homy ->
                homy.onboardingId
            }
        ) { homy ->
            HomyChip(
                name = homy.nickname,
                homyTypeOrdinal = homy.color.ordinal
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TodoDetailBottomSheetPreview() {
    HousTheme {
        Box(modifier = Modifier.background(Color.White)) {
            TodoDetailBottomSheet(
                todoDetail = TodoDetail(
                    name = "청소기 좀 돌려라",
                    selectedUsers = listOf(
                        User(onboardingId = 1, nickname = "강원용", color = HomyType.BLUE),
                        User(onboardingId = 2, nickname = "이영주", color = HomyType.RED),
                        User(onboardingId = 3, nickname = "이준원", color = HomyType.PURPLE)
                    ),
                    dayOfWeeks = "화,수,목,금"
                ),
                editAction = {},
                deleteAction = {}
            )
        }
    }
}
