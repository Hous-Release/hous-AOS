package hous.release.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG2
import hous.release.designsystem.theme.HousG3
import hous.release.designsystem.theme.HousG7
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousEditRule(
    text: String
) {
    HousRuleSlot(
        text = text,
        isShowTrailingIcon = true,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(HousG3)
            )
        },
        trailingIcon = {
            Image(
                modifier = Modifier.padding(end = 20.dp),
                painter = painterResource(id = R.drawable.ic_move),
                contentDescription = null
            )
        }
    )
}

@Composable
fun HousRule(
    text: String,
    isNew: Boolean
) {
    HousRuleSlot(
        text = text,
        isShowTrailingIcon = isNew,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (isNew) HousBlueL1 else HousG3)
            )
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

@Composable
private fun HousRuleSlot(
    text: String,
    isShowTrailingIcon: Boolean,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
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

@Composable
@Preview(showBackground = true)
fun HousRulePreview() {
    HousTheme {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            HousRule(
                text = "하이",
                isNew = true
            )
            HousRule(
                text = "하이",
                isNew = false
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HousEditRulePreview() {
    HousTheme {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            HousEditRule(
                text = "하이"
            )
            HousEditRule(
                text = "하이"
            )
        }
    }
}
