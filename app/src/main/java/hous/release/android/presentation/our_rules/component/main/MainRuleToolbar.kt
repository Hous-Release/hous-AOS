package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.HousToolbarSlot
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme
import hous.release.android.R.drawable as app
import hous.release.designsystem.R.drawable as feature

@Composable
fun MainRuleToolbar(
    onBack: () -> Unit = {},
    onClickSetting: () -> Unit = {},
    title: String = "우리 집 Rules"
) {
    HousToolbarSlot(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 24.dp, end = 8.dp),
        leadingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    onBack()
                },
                tint = HousG4,
                painter = painterResource(id = feature.ic_back),
                contentDescription = null
            )
        },
        title = title,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    onClickSetting()
                },
                tint = HousG4,
                painter = painterResource(id = app.ic_our_rules_setting),
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
fun PreviewMainRuleToolbar() {
    HousTheme {
        Surface {
            MainRuleToolbar()
        }
    }
}
