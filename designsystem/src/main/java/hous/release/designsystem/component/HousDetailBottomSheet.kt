package hous.release.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousDetailBottomSheet(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    editAction: () -> Unit,
    deleteAction: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        content()
        BottomSheetButton(
            text = stringResource(R.string.todo_detail_bs_edit),
            color = HousBlack,
            action = editAction,
            topPadding = 18.dp,
            bottomPadding = 14.dp
        )
        BottomSheetButton(
            text = stringResource(R.string.todo_detail_bs_delete),
            color = HousRed,
            action = deleteAction,
            topPadding = 18.dp,
            bottomPadding = 22.dp
        )
    }
}

@Composable
private fun BottomSheetButton(
    text: String,
    color: Color,
    topPadding: Dp,
    bottomPadding: Dp,
    action: () -> Unit
) {
    Divider(modifier = Modifier.height(1.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action() }
            .padding(top = topPadding, bottom = bottomPadding),
        text = text,
        color = color,
        style = HousTheme.typography.b2,
        textAlign = TextAlign.Center
    )
}
