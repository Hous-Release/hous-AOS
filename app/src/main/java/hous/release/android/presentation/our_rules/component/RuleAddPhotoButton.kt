package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme

@Composable
fun RuleAddPhotoButton(
    isActiveButton: Boolean = true,
    onClick: () -> Unit = {}
) {
    // button icon and Text("추가하기)
    Row(
        modifier = Modifier.clickable(
            enabled = isActiveButton,
            onClick = onClick
        ),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RuleAddIcon(
            color = if (isActiveButton) HousBlue else HousG4
        )
        Text(
            text = "추가하기",
            style = HousTheme.typography.b3,
            color = if (isActiveButton) HousBlue else HousG4
        )
    }
}

@Composable
private fun RuleAddIcon(
    color: Color = HousBlue
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .background(color)
            .size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(11.dp),
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null
        )
    }
}

@Composable
@Preview(name = "active된 photo add button", showBackground = true)
private fun Preview3() {
    HousTheme {
        RuleAddPhotoButton()
    }
}

@Composable
@Preview(name = "inActive된 photo add button", showBackground = true)
private fun Preview4() {
    HousTheme {
        RuleAddPhotoButton(false)
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    HousTheme {
        RuleAddIcon()
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview2() {
    HousTheme {
        RuleAddIcon(color = HousG4)
    }
}
