package hous.release.android.presentation.our_rules.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.our_rules.component.RuleToolbar
import hous.release.android.presentation.our_rules.component.represent.RepresentRuleList
import hous.release.android.presentation.our_rules.model.RepresentRuleUiModel
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.Rule

@Composable
fun RepresentRuleScreen(
    rules: List<RepresentRuleUiModel> = emptyList(),
    isChanged: Boolean = false,
    onSave: () -> Unit = {},
    onBack: () -> Unit = {},
    onRuleClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        RuleToolbar(
            title = stringResource(id = R.string.our_rule_represent_rule_title),
            trailingTitle = stringResource(id = R.string.our_rule_save_new_rule),
            isButtonActive = isChanged,
            onBack = onBack,
            onAddButton = onSave
        )
        Spacer(modifier = Modifier.height(20.dp))
        RepresentRuleList(
            modifier = Modifier.padding(horizontal = 16.dp),
            rules = rules,
            onClick = onRuleClick
        )
    }
}

@Preview("대표룰 Screen - General", showBackground = true)
@Composable
private fun Preview1() {
    HousTheme {
        RepresentRuleScreen(
            rules = listOf(
                Rule(
                    id = 1,
                    name = "text1",
                    isRepresent = true
                ),
                Rule(id = 2, "text 2")
            ).map { RepresentRuleUiModel.from(it) }
        )
    }
}

@Preview("대표룰 Screen - Empty", showBackground = true)
@Composable
private fun Preview2() {
    HousTheme {
        RepresentRuleScreen()
    }
}
