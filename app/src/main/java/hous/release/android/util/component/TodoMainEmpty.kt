package hous.release.android.util.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.theme.HousTheme

@Composable
fun TodoMainEmpty() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.hous_blue_l2_80))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.todo_main_empty_progress),
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.hous_g_5),
                style = HousTheme.typography.description,
            )
            Spacer(modifier = Modifier.height(13.dp))
            RoundedLinearIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                progress = 0.0f,
                trackColor = colorResource(id = R.color.hous_blue_l2),
                backgroundColor = colorResource(id = R.color.hous_blue_l2)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoMainEmptyPreview() {
    TodoMainEmpty()
}
