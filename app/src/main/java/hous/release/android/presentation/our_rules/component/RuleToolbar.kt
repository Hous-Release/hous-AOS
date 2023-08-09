package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.component.HousToolbarSlot
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme
import hous.release.android.R as hous

@Composable
fun RuleToolbar(
    modifier: Modifier = Modifier,
    isButtonActive: Boolean = false,
    title: String = stringResource(id = hous.string.our_rule_add_new_rule_title),
    trailingTitle: String = stringResource(id = hous.string.our_rule_add_new_rule),
    onAddButton: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    HousToolbarSlot(
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp, end = 24.dp),
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
            Text(
                modifier = Modifier.then(
                    if (isButtonActive) Modifier.clickable { onAddButton() }
                    else Modifier
                ),
                text = trailingTitle,
                style = HousTheme.typography.b2,
                color = if (isButtonActive) HousBlue else HousG4
            )
        }
    )
}

@Preview(name = "추가 버튼이 활성화된 Tool Bar")
@Composable
fun PreviewMainRuleToolbar() {
    HousTheme {
        Surface {
            RuleToolbar(
                title = "Rules 추가",
                trailingTitle = "추가",
                isButtonActive = true
            )
        }
    }
}

@Preview(name = "추가 버튼이 비활성화된 Tool Bar")
@Composable
fun PreviewMainRuleToolbar2() {
    HousTheme {
        Surface {
            RuleToolbar(
                title = "Rules 추가",
                trailingTitle = "추가",
                isButtonActive = false
            )
        }
    }
}
