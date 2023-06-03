package hous.release.feature.todo.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.component.HousToolbarSlot

@Composable
fun TodoDetailToolbar(
    finish: () -> Unit
) {
    HousToolbarSlot(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp),
        leadingIcon = {
            Icon(
                modifier = Modifier.clickable { finish() },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null
            )
        },
        title = stringResource(hous.release.feature.todo.R.string.todo_detail_title)
    )
}