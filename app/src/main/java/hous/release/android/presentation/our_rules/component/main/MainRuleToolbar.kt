package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.component.HousToolbarSlot
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme

@Composable
fun MainRuleToolbar(
    onNavigateToRepresentRule: () -> Unit = {},
    modifier: Modifier = Modifier,
    title: String = "우리 집 Rules",
    onBack: () -> Unit = { }
) {
    var expanded by remember { mutableStateOf(false) }
    val onDismiss = { expanded = false }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
    ) {
        HousToolbarSlot(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 24.dp, end = 8.dp),
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onBack()
                    },
                    tint = HousG4,
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null
                )
            },
            title = title,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        expanded = true
                    },
                    tint = HousG4,
                    painter = painterResource(id = hous.release.android.R.drawable.ic_our_rules_setting),
                    contentDescription = null
                )
            }
        )
        MainRuleDropDownMenu(expanded, onDismiss, onNavigateToRepresentRule)
    }
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
