package hous.release.android.presentation.todo.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R

@Composable
fun DailyTab(
    currIndex: Int,
    weekOfDay: Array<String> = stringArrayResource(id = R.array.to_do_week_of_day),
    setCurrIndex: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(weekOfDay) { index, item ->
            DailyTapItem(item, index, currIndex, setCurrIndex)
        }
    }
}

@Composable
private fun DailyTapItem(
    weekOfDay: String,
    index: Int,
    currIndex: Int,
    setCurrIndex: (Int) -> Unit
) {
    val modifier = Modifier
        .clip(RoundedCornerShape(40.dp))
        .background(color = colorResource(id = R.color.hous_blue))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = if (index == currIndex) modifier.padding(
                vertical = 10.dp,
                horizontal = 13.dp
            ) else Modifier.clickable { setCurrIndex(index) }
                .padding(vertical = 10.dp, horizontal = 13.dp),
            text = weekOfDay,
            color = colorResource(id = if (index == currIndex) R.color.hous_white else R.color.hous_g_5)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (index == currIndex) {
            Image(
                painter = painterResource(id = R.drawable.ic_daily_tab_indicator),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun DailyTapPreview() {
    val weekOfDay = listOf("월", "화", "수", "목", "금", "토", "일")
    val currIndex = remember {
        mutableStateOf(1)
    }
    val setCurrIndex: (Int) -> Unit = { index ->
        currIndex.value = index
    }
    LazyRow {
        itemsIndexed(weekOfDay) { index, item ->
            DailyTapItem(item, index, currIndex.value, setCurrIndex)
        }
    }
}
