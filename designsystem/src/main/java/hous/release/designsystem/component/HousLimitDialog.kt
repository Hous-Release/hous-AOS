package hous.release.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousLimitDialog(
    title: String,
    content: String,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            HousLimitDialogFrame(
                title = title,
                content = content,
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun HousLimitDialogFrame(
    title: String,
    content: String,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = HousTheme.typography.h4,
            color = HousBlack
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_todo_limit),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = content,
            style = HousTheme.typography.b2,
            color = HousBlack,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.clickable {
                onDismissRequest()
            },
            text = stringResource(R.string.todo_limit_dialog_check),
            style = HousTheme.typography.b3,
            color = HousG5
        )
    }
}
