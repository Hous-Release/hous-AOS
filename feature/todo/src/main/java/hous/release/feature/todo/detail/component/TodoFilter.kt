package hous.release.feature.todo.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousBlueL2
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite
import hous.release.feature.todo.R

@Composable
fun TodoFilter(
    selectedDayOfWeek: String,
    selectedHomies: String,
    isShowBottomSheet: Boolean,
    showFilterBottomSheet: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(if (isShowBottomSheet) HousBlue else HousBlueL2)
            .clickable { showFilterBottomSheet() }
            .padding(
                vertical = 6.dp,
                horizontal = 12.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedDayOfWeek.isBlank() && selectedHomies.isBlank()) {
                Icon(
                    painter = painterResource(id = hous.release.designsystem.R.drawable.ic_filter),
                    contentDescription = null,
                    tint = if (isShowBottomSheet) HousWhite else HousG6
                )
                Text(
                    text = stringResource(R.string.todo_filter),
                    style = HousTheme.typography.description,
                    color = if (isShowBottomSheet) HousWhite else HousG6
                )
            } else {
                FilterContent(
                    selectedDayOfWeek,
                    selectedHomies
                )
            }
        }
    }
}

@Composable
private fun FilterContent(
    selectedDayOfWeek: String,
    selectedHomies: String
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (selectedDayOfWeek.isNotBlank()) {
            FilterTextSlot(
                prefixText = stringResource(R.string.todo_filter_day_of_week),
                tailText = selectedDayOfWeek
            )
        }
        if (selectedDayOfWeek.isNotBlank() && selectedHomies.isNotBlank()) {
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(HousBlueL1)
            )
        }
        if (selectedHomies.isNotBlank()) {
            FilterTextSlot(
                prefixText = stringResource(R.string.todo_filter_homy),
                tailText = selectedHomies
            )
        }
    }
}

@Composable
private fun FilterTextSlot(
    prefixText: String,
    tailText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = prefixText,
            color = HousG6,
            style = HousTheme.typography.description
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(3.dp)
                .background(HousBlue)
        )
        Text(
            text = tailText,
            color = HousBlue,
            style = HousTheme.typography.description
        )
    }
}

@Preview
@Composable
private fun FilterPreview() {
    HousTheme {
        TodoFilter(
            selectedDayOfWeek = "월, 화, 수, 목, 금",
            selectedHomies = "강원용 외 2명",
            isShowBottomSheet = false,
            showFilterBottomSheet = {}
        )
    }
}
