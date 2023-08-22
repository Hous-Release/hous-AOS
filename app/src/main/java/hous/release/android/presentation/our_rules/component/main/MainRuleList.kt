package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.component.HousDot
import hous.release.designsystem.component.HousDotType
import hous.release.designsystem.component.HousRuleSlot
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.MainRule

@Composable
fun MainRuleList(
    onNavigateToDetailRule: (Int) -> Unit = {},
    mainRules: List<MainRule> = emptyList()
) {
    if (mainRules.isEmpty()) {
        MainRuleEmptyContent()
    } else {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(mainRules, key = { _, rule -> rule.id }) { _, rule ->
                MainRuleItem(
                    onClick = { onNavigateToDetailRule(rule.id) },
                    mainRule = rule
                )
            }
        }
    }
}

@Composable
private fun MainRuleEmptyContent() {
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

@Composable
private fun MainRuleItem(
    onClick: () -> Unit = {},
    mainRule: MainRule = MainRule()
) {
    val focusManager = LocalFocusManager.current
    HousRuleSlot(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                focusManager.clearFocus()
                onClick()
            })
            .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
        text = mainRule.name,
        isShowTrailingIcon = mainRule.isNew,
        leadingIcon = {
            HousDot(HousDotType.from(isNew = mainRule.isNew, isRepresent = mainRule.isRepresent))
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

@Preview(name = "empty content", showBackground = true)
@Composable
private fun EmptyContentPreview() {
    HousTheme {
        Surface {
            MainRuleList()
        }
    }
}

@Preview(name = "MainRuleContent", showBackground = true)
@Composable
private fun MainRuleContentPreview() {
    HousTheme {
        MainRuleList(
            mainRules = listOf(
                MainRule().copy(id = 1, name = "test1", isNew = true),
                MainRule().copy(id = 2, name = "test2", isNew = false),
                MainRule().copy(id = 3, name = "test3", isNew = true),
                MainRule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}

@Preview(name = "main rule Item", showBackground = true)
@Composable
private fun MainRulePreview() {
    HousTheme {
        Surface {
            MainRuleItem()
        }
    }
}

@Preview(name = "new main rule Item", showBackground = true)
@Composable
private fun NewMainRulePreview() {
    HousTheme {
        Surface {
            MainRuleItem(
                mainRule = MainRule().copy(isNew = true)
            )
        }
    }
}
