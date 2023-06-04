package hous.release.feature.todo.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.HousRuleSlot
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG3
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.todo.TodoWithNew
import hous.release.feature.todo.R

@Composable
fun ToDoItem(
    todo: TodoWithNew,
    showToDoDetailBottomSheet: () -> Unit
) {
    HousRuleSlot(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showToDoDetailBottomSheet() }
            .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
        text = todo.name,
        isShowTrailingIcon = todo.isNew,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (todo.isNew) HousBlueL1 else HousG3)
            )
        },
        trailingIcon = {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = stringResource(R.string.new_todo),
                color = HousBlue,
                style = HousTheme.typography.en2
            )
        }
    )
}
