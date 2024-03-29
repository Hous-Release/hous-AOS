package hous.release.feature.todo.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.DayItem
import hous.release.designsystem.component.HomyChip
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite
import hous.release.domain.entity.HomyType
import hous.release.feature.todo.R
import hous.release.feature.todo.detail.SelectableDayOfWeek
import hous.release.feature.todo.detail.SelectableHomy

@Composable
fun FilterBottomSheet(
    homies: List<SelectableHomy>,
    selectableWeek: List<SelectableDayOfWeek>,
    selectDayOfWeek: (dayIdx: Int) -> Unit,
    selectHomy: (homyIdx: Int) -> Unit,
    getTodosAppliedFilter: () -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 24.dp,
            bottom = 20.dp
        )
    ) {
        Text(
            text = stringResource(R.string.filter_title),
            color = HousBlack,
            style = HousTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.filter_day),
            color = HousG5,
            style = HousTheme.typography.b1
        )
        Spacer(modifier = Modifier.height(8.dp))
        SelectableWeek(
            selectableWeek = selectableWeek,
            selectDayOfWeek = selectDayOfWeek
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.filter_homy),
            color = HousG5,
            style = HousTheme.typography.b1
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomyChips(
            homies = homies,
            selectHomy = selectHomy
        )
        Spacer(modifier = Modifier.height(28.dp))
        FilterButton(getTodosAppliedFilter = getTodosAppliedFilter)
    }
}

@Composable
private fun SelectableWeek(
    selectableWeek: List<SelectableDayOfWeek>,
    selectDayOfWeek: (dayIdx: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(selectableWeek) { idx, selectableDayOfWeek ->
            DayItem(
                dayIdx = idx,
                text = selectableDayOfWeek.dayOfWeek,
                selectTodoDay = { selectDayOfWeek(idx) },
                isSelected = selectableDayOfWeek.isSelected
            )
        }
    }
}

@Composable
private fun HomyChips(
    homies: List<SelectableHomy>,
    selectHomy: (homyIdx: Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(homies) { idx, homy ->
            HomyChip(
                name = homy.name,
                homyTypeOrdinal = homy.homyType.ordinal,
                index = idx,
                isClickable = true,
                isSelected = homy.isSelected,
                onClick = { selectHomy(idx) }
            )
        }
    }
}

@Composable
private fun FilterButton(
    getTodosAppliedFilter: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { getTodosAppliedFilter() }
            .background(HousBlue)
            .padding(top = 10.dp, bottom = 9.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.filter_save),
            color = HousWhite,
            style = HousTheme.typography.b1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterBottomSheetPreview() {
    HousTheme {
        Box(modifier = Modifier.background(HousWhite)) {
            FilterBottomSheet(
                homies = listOf(
                    SelectableHomy(id = 0, name = "KWY", homyType = HomyType.BLUE),
                    SelectableHomy(id = 0, name = "SYJ", homyType = HomyType.BLUE),
                    SelectableHomy(
                        id = 0,
                        name = "LJW",
                        isSelected = true,
                        homyType = HomyType.BLUE
                    ),
                    SelectableHomy(
                        id = 0,
                        name = "LYJ",
                        isSelected = true,
                        homyType = HomyType.BLUE
                    ),
                ),
                selectableWeek = listOf(
                    SelectableDayOfWeek(dayOfWeek = "월"),
                    SelectableDayOfWeek(dayOfWeek = "화"),
                    SelectableDayOfWeek(dayOfWeek = "수"),
                    SelectableDayOfWeek(dayOfWeek = "목"),
                    SelectableDayOfWeek(dayOfWeek = "금"),
                    SelectableDayOfWeek(dayOfWeek = "토"),
                    SelectableDayOfWeek(dayOfWeek = "일")
                ),
                selectDayOfWeek = {},
                selectHomy = {},
                getTodosAppliedFilter = {}
            )
        }
    }
}
