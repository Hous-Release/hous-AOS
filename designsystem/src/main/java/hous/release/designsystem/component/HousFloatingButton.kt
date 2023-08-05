package hous.release.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousWhite

@Composable
fun FabScreenSlot(
    modifier: Modifier = Modifier,
    fabOnClick: () -> Unit,
    content: @Composable () -> Unit
) {
    content()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 12.dp, bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        HousFloatingButton { fabOnClick() }
    }
}

@Composable
fun HousFloatingButton(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = HousBlue,
        contentColor = HousWhite,
        modifier = Modifier.size(92.dp).padding(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
            contentDescription = "Add"
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    HousFloatingButton()
}

@Composable
@Preview(widthDp = 360, heightDp = 640, showBackground = true)
fun PreviewFabContainerWithContent() {
    FabScreenSlot(
        fabOnClick = {},
        content = {
        }
    )
}
