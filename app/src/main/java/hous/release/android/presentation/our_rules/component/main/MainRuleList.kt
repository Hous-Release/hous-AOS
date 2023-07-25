package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.HousDash
import hous.release.designsystem.component.HousRuleSlot
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.MainRule

@Composable
fun MainRuleList(
    onNavigateToDetailRule: (Int) -> Unit = {},
    mainRules: List<MainRule> = emptyList()
) {
    LazyColumn {
        itemsIndexed(mainRules, key = { _, rule -> rule.id }) { _, rule ->
            MainRuleItem(
                onClick = { onNavigateToDetailRule(rule.id) },
                mainRule = rule
            )
        }
    }
}

@Composable
private fun MainRuleItem(
    onClick: () -> Unit = {},
    mainRule: MainRule = MainRule()
) {
    HousRuleSlot(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 6.dp, top = 12.dp, bottom = 12.dp),
        text = mainRule.name,
        isShowTrailingIcon = mainRule.isNew,
        leadingIcon = {
            HousDash(mainRule.isNew)
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
