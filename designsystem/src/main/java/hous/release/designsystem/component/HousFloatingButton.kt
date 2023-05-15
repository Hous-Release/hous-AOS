package hous.release.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue

@Composable
fun FabScreenSlot(
    fabOnClick: () -> Unit,
    content: @Composable () -> Unit
) {
    content()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 28.dp, bottom = 36.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        HousFloatingButton { fabOnClick() }
    }
}

@Composable
fun HousFloatingButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(HousBlue)
            .clickable { onClick() }
            .size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null
        )
    }
}
