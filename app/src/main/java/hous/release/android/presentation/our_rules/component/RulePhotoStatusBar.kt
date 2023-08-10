package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme

@Composable
fun RulePhotoStatusBar(
    modifier: Modifier = Modifier,
    photoCount: Int = 0,
    onOpenGallery: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RuleAddPhotoButton(photoCount in (0..4), onOpenGallery)

        Text(
            text = "$photoCount/5",
            style = HousTheme.typography.en2,
            color = HousG4
        )
    }
}

@Preview(name = "active", showBackground = true)
@Composable
private fun RulePhotoStatusBarPreview() {
    HousTheme {
        RulePhotoStatusBar(photoCount = 3)
    }
}

@Preview(name = "inactive", showBackground = true)
@Composable
private fun RulePhotoStatusBarPreview2() {
    HousTheme {
        RulePhotoStatusBar(photoCount = 5)
    }
}
