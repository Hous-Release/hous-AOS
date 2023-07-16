package hous.release.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG3
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousDash(
    isNew: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(if (isNew) HousBlueL1 else HousG3)
    )
}

@Preview(name = "newType - hous dash", showBackground = true)
@Composable
private fun HousDashPreview() {
    HousTheme {
        HousDash(isNew = true)
    }
}

@Preview(name = "hous dash", showBackground = true)
@Composable
private fun HousDashPreview2() {
    HousTheme {
        HousDash()
    }
}
