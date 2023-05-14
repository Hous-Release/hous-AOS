package hous.release.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.designsystem.R
import hous.release.designsystem.constant.HomyType
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG1
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite

@Composable
fun HomyChip(
    name: String,
    homyTypeOrdinal: Int,
    index: Int = -1,
    isClickable: Boolean = false,
    isSelected: Boolean = false,
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .clickable(isClickable) { onClick(index) }
            .background(if (isSelected) HousBlue else HousG1)
            .padding(start = 6.dp, end = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.ic_homy_check),
                contentDescription = null
            )
        } else {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(HomyType(homyTypeOrdinal).getColor())
                    .size(12.dp)
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = name,
            color = if (isSelected) HousWhite else HousG6,
            style = HousTheme.typography.b3.copy(
                letterSpacing = (-0.3).sp,
                lineHeight = 18.sp
            ),
            modifier = Modifier
                .padding(top = 5.dp, bottom = 3.dp)
        )
    }
}

@Preview(name = "non-click homy chip")
@Composable
private fun NonClickHomyChipPreview() {
    HousTheme {
        HomyChip("강원용", 3)
    }
}

@Preview(name = "clickable homy chip")
@Composable
private fun ClickableHomyChipPreview() {
    HousTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomyChip(
                "강원용",
                3,
                index = 0,
                isClickable = true,
                isSelected = false
            )
            HomyChip(
                "이준원",
                1,
                index = 1,
                isClickable = true,
                isSelected = true
            )
        }
    }
}
