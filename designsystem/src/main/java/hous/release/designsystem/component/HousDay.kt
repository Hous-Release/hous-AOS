package hous.release.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG1
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme

@Composable
fun DayItem(
    hideKeyBoard: () -> Unit,
    userIdx: Int,
    dayIdx: Int,
    text: String,
    selectTodoDay: (userIdx: Int, dayIdx: Int) -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                color = if (isSelected) HousBlueL1 else HousG1
            )
            .clickable {
                selectTodoDay(userIdx, dayIdx)
                hideKeyBoard()
            }
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) HousBlue else HousG4,
            style = HousTheme.typography.b3
        )
    }
}

@Composable
fun DayItem(
    dayIdx: Int,
    text: String,
    selectTodoDay: (dayIdx: Int) -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = if (isSelected) HousBlueL1 else HousG1)
            .clickable { selectTodoDay(dayIdx) }
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) HousBlue else HousG4,
            style = HousTheme.typography.b3
        )
    }
}

@Composable
@Preview
fun DayPreView() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    HousTheme {
        DayItem(
            dayIdx = 0,
            text = "월",
            isSelected = isSelected,
            selectTodoDay = { isSelected = !isSelected }
        )
    }
}
