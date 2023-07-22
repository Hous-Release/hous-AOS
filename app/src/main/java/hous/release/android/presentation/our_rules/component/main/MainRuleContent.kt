package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.MainRule

@Composable
fun MainRuleContent(
    onNavigateToDetailRule: (Int) -> Unit = {},
    mainRules: List<MainRule> = emptyList()
) {
    if (mainRules.isEmpty()) {
        MainRuleEmptyContent()
    } else {
        Spacer(modifier = Modifier.height(16.dp))
        MainRuleList(
            onNavigateToDetailRule = onNavigateToDetailRule,
            mainRules = mainRules
        )
    }
}

@Composable
fun MainRuleEmptyContent() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 88.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.hous_empty_our_rules),
            color = HousG5,
            style = HousTheme.typography.b2
        )
    }
}

@Preview(name = "empty content", showBackground = true)
@Composable
private fun EmptyContentPreview() {
    HousTheme {
        Surface {
            MainRuleContent()
        }
    }
}

@Preview(name = "MainRuleContent", showBackground = true)
@Composable
private fun MainRuleContentPreview() {
    HousTheme {
        MainRuleContent(
            mainRules = listOf(
                MainRule().copy(id = 1, name = "test1", isNew = true),
                MainRule().copy(id = 2, name = "test2", isNew = false),
                MainRule().copy(id = 3, name = "test3", isNew = true),
                MainRule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}
