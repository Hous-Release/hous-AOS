package hous.release.android.util.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.component.DayItem
import hous.release.designsystem.theme.HousTheme

@Composable
fun DayItems(
    userIdx: Int,
    dayList: List<Boolean>,
    selectTodoDay: (userIdx: Int, dayIdx: Int) -> Unit = { _, _ ->
    },
    hideKeyBoard: () -> Unit = {},
    dayNameList: Array<String> = stringArrayResource(id = R.array.to_do_week_of_day)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(7) { idx ->
            DayItem(
                hideKeyBoard = hideKeyBoard,
                userIdx = userIdx,
                dayIdx = idx,
                text = dayNameList[idx],
                selectTodoDay = selectTodoDay,
                isSelected = dayList[idx]
            )
        }
    }
}

@Composable
@Preview
fun DayItemsPreView() {
    val selectedList = remember {
        mutableStateListOf<Boolean>().apply { addAll(List(7) { false }) }
    }
    val selectTodoDay: (userIdx: Int, dayIdx: Int) -> Unit = { _, dayIdx ->
        selectedList[dayIdx] = !selectedList[dayIdx]
    }
    HousTheme {
        DayItems(
            userIdx = 0,
            dayList = selectedList,
            selectTodoDay = selectTodoDay
        )
    }
}
