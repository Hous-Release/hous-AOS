package hous.release.android.util.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.util.theme.b3

@Composable
fun DayItems(
    userIdx: Int,
    dayList: List<Boolean>,
    selectTodoDay: (userIdx: Int, dayIdx: Int) -> Unit = { _, _ ->
    },
    hideKeyBoard: () -> Unit = {},
    dayNameList: List<String> = listOf("월", "화", "수", "목", "금", "토", "일")
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(7) { idx ->
            DayItem(
                hideKeyBoard,
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
fun DayItem(
    hideKeyBoard: () -> Unit = {},
    userIdx: Int,
    dayIdx: Int,
    text: String,
    selectTodoDay: (userIdx: Int, dayIdx: Int) -> Unit = { _, _ -> {} },
    isSelected: Boolean
) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = colorResource(id = R.color.hous_blue_l1))
                .clickable {
                    selectTodoDay(userIdx, dayIdx)
                    hideKeyBoard()
                }
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = colorResource(id = R.color.hous_blue),
                style = b3.copy(
                    fontSize = dpToSp(dp = 13.dp)
                )
            )
        }
        return
    }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.hous_g_1))
            .clickable {
                selectTodoDay(userIdx, dayIdx)
                hideKeyBoard()
            }
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.hous_g_4),
            style = b3.copy(
                fontSize = dpToSp(dp = 13.dp)
            )
        )
    }
}

@Composable
@Preview
fun DayPreView() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    MaterialTheme {
        DayItem(userIdx = 0, dayIdx = 0, text = "월", isSelected = isSelected)
    }
}

@Composable
@Preview
fun DayItemsPreView() {
    val selectedList = remember {
        mutableStateListOf<Boolean>().apply { addAll(List(7) { false }) }
    }
    MaterialTheme {
        DayItems(
            userIdx = 0,
            dayList = selectedList
        )
    }
}
