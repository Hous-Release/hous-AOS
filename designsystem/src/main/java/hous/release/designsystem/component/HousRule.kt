package hous.release.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG2
import hous.release.designsystem.theme.HousG7
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousRuleSlot(
    modifier: Modifier,
    text: String,
    isShowTrailingIcon: Boolean,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon()
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                color = HousG7,
                style = HousTheme.typography.b2
            )
            if (isShowTrailingIcon) trailingIcon()
        }
        Divider(
            modifier = Modifier.height(1.dp),
            color = HousG2
        )
    }
}

@Preview(name = "hous edit", showBackground = true)
@Composable
fun HousEditRulePreview() {
    HousTheme {
        HousRuleSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
            text = "text",
            isShowTrailingIcon = false,
            leadingIcon = {
                HousDot(true)
            },
            trailingIcon = {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "new !",
                    color = HousBlue,
                    style = HousTheme.typography.en2
                )
            }
        )
    }
}

@Composable
@Preview(name = "hous rule", showBackground = true)
fun HousRulePreview() {
    HousTheme {
        HousRuleSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
            text = "text",
            isShowTrailingIcon = false,
            leadingIcon = {
                HousDot()
            },
            trailingIcon = {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "new !",
                    color = HousBlue,
                    style = HousTheme.typography.en2
                )
            }
        )
    }
}
