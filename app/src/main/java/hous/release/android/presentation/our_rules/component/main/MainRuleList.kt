package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.our_rules.component.RuleEmptyContent
import hous.release.designsystem.component.HousDot
import hous.release.designsystem.component.HousDotType
import hous.release.designsystem.component.HousRuleSlot
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.Rule

@Composable
fun MainRuleList(
    onNavigateToDetailRule: (Int) -> Unit = {},
    originRules: List<Rule> = emptyList(),
    filteredRules: List<Rule> = emptyList()
) {
    when {
        originRules.isEmpty() -> {
            RuleEmptyContent(text = stringResource(id = R.string.hous_empty_our_rules))
        }
        filteredRules.isEmpty() -> {
            RuleEmptyContent(text = stringResource(id = R.string.hous_filterd_empty_our_rules))
        }
        else -> {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                itemsIndexed(filteredRules, key = { _, rule -> rule.id }) { _, rule ->
                    MainRuleItem(
                        onClick = { onNavigateToDetailRule(rule.id) },
                        mainRule = rule
                    )
                }
            }
        }
    }
}

@Composable
private fun MainRuleItem(
    onClick: () -> Unit = {},
    mainRule: Rule = Rule()
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

@Preview(name = "empty origin rules content", showBackground = true)
@Composable
private fun EmptyContentPreview() {
    HousTheme {
        Surface {
            MainRuleList()
        }
    }
}

@Preview(name = "empty filtered Rules", showBackground = true)
@Composable
private fun EmptyContentPreview2() {
    HousTheme {
        Surface {
            MainRuleList(originRules = listOf(Rule().copy(id = 1, name = "test1")))
        }
    }
}

@Preview(name = "MainRuleContent", showBackground = true)
@Composable
private fun MainRuleContentPreview() {
    HousTheme {
        MainRuleList(
            filteredRules = listOf(
                Rule().copy(id = 1, name = "test1", isNew = true),
                Rule().copy(id = 2, name = "test2", isNew = false),
                Rule().copy(id = 3, name = "test3", isNew = true),
                Rule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}

@Preview(name = "general rule Item", showBackground = true)
@Composable
private fun Preview1() {
    HousTheme {
        Surface {
            MainRuleItem()
        }
    }
}

@Preview(name = "Represent rule Item", showBackground = true)
@Composable
private fun Preview2() {
    HousTheme {
        Surface {
            MainRuleItem(
                mainRule = Rule().copy(isRepresent = true)
            )
        }
    }
}

@Preview(name = "new rule Item", showBackground = true)
@Composable
private fun Preview3() {
    HousTheme {
        Surface {
            MainRuleItem(
                mainRule = Rule().copy(isNew = true)
            )
        }
    }
}

@Preview(name = "Represent and New rule Item", showBackground = true)
@Composable
private fun Preview4() {
    HousTheme {
        Surface {
            MainRuleItem(
                mainRule = Rule().copy(isNew = true, isRepresent = true)
            )
        }
    }
}
