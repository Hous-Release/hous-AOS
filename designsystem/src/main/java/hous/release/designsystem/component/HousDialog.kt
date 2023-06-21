package hous.release.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousDialog(
    title: String,
    content: String,
    neutralText: String,
    actionText: String,
    actionOnClick: () -> Unit,
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
            HousDialogFrame(
                title = title,
                content = content,
                neutralText = neutralText,
                actionText = actionText,
                actionOnClick = actionOnClick,
                neutralOnClick = onDismissRequest
            )
        }
    }
}

@Composable
private fun HousDialogFrame(
    title: String,
    content: String,
    neutralText: String,
    actionText: String,
    neutralOnClick: () -> Unit,
    actionOnClick: () -> Unit
) {
    HousDialogSlot(
        title = title,
        content = content,
        neutralButton = {
            Text(
                modifier = Modifier
                    .clickable { neutralOnClick() },
                text = neutralText,
                style = HousTheme.typography.b2,
                color = HousG5
            )
        },
        actionButton = {
            Text(
                modifier = Modifier
                    .clickable { actionOnClick() },
                text = actionText,
                style = HousTheme.typography.b2,
                color = HousRed
            )
        }
    )
}

@Composable
private fun HousDialogSlot(
    title: String,
    content: String,
    neutralButton: @Composable () -> Unit,
    actionButton: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                bottom = 20.dp,
                start = 24.dp,
                end = 30.dp
            )
    ) {
        Text(
            text = title,
            style = HousTheme.typography.h4,
            color = HousBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            style = HousTheme.typography.description,
            color = HousG5
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            neutralButton()
            Spacer(modifier = Modifier.width(32.dp))
            actionButton()
        }
    }
}

@Preview(
    showBackground = true,
    name = "api 연결 없는 다이얼로그"
)
@Composable
private fun HousDialogPreview() {
    HousTheme {
        HousDialog(
            title = "수정사항이 저장되지 않았어요!",
            content = "정말 취소하려면 나가기 버튼을 눌러주세요.",
            neutralText = "계속 수정하기",
            actionText = "나가기",
            onDismissRequest = { /*TODO*/ },
            actionOnClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "api 연결하는 다이얼로그"
)
@Composable
private fun HousDialogPreview2() {
    val method: (Int) -> Unit = {}
    val id = 0
    HousTheme {
        HousDialog(
            title = "수정사항이 저장되지 않았어요!",
            content = "정말 취소하려면 나가기 버튼을 눌러주세요.",
            neutralText = "계속 수정하기",
            actionText = "나가기",
            onDismissRequest = { /*TODO*/ },
            actionOnClick = { method(id) }
        )
    }
}
