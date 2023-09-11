package hous.release.android.presentation.our_rules.component.represent

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.presentation.our_rules.component.RuleEmptyContent
import hous.release.android.presentation.our_rules.model.RepresentRuleUiModel
import hous.release.designsystem.component.HousCheckBox
import hous.release.designsystem.component.HousRuleSlot
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.Rule

@Composable
fun RepresentRuleList(
    modifier: Modifier = Modifier,
    rules: List<RepresentRuleUiModel> = emptyList(),
    onClick: (Int) -> Unit = {}
) {
    if (rules.isEmpty()) {
        RuleEmptyContent()
    }
    LazyColumn(modifier = modifier) {
        itemsIndexed(rules, key = { _, rule -> rule.id }) { _, rule ->
            RepresentRuleItem(rule, onClick)
        }
    }
}

@Composable
private fun RepresentRuleItem(
    rule: RepresentRuleUiModel,
    onClick: (Int) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    HousRuleSlot(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                onClick = { onClick(rule.id) },
                indication = LocalIndication.current
            )
            .padding(start = 8.dp, top = 12.dp, bottom = 12.dp),
        text = rule.name,
        isShowTrailingIcon = true,
        leadingIcon = {
            HousCheckBox(
                interactionSource = interactionSource,
                onCheckedChange = { onClick(rule.id) },
                isChecked = rule.isRepresent
            )
        },
        trailingIcon = {}
    )
}

@Preview(name = "RepresentRuleList - General", showBackground = true)
@Composable
private fun Preview1() {
    HousTheme {
        var rules by remember {
            mutableStateOf(
                listOf(
                    Rule(
                        id = 1,
                        name = "text1",
                        isRepresent = true
                    ),
                    Rule(id = 2, "text 2")
                ).map { RepresentRuleUiModel.from(it) }
            )
        }
        val onClick: (Int) -> Unit = { id ->
            rules = rules.map {
                if (it.id == id) {
                    it.copy(isRepresent = !it.isRepresent)
                } else {
                    it
                }
            }
        }
        RepresentRuleList(rules = rules, onClick = onClick)
    }
}

@Preview(name = "RepresentRuleList - Empty Rule", showBackground = true)
@Composable
private fun Preview2() {
    HousTheme {
        RepresentRuleList()
    }
}

@Preview(name = "RepresentRuleItem", showBackground = true)
@Composable
private fun Preview3() {
    HousTheme {
        var isRepresent by remember { mutableStateOf(false) }
        val onClick: (Int) -> Unit = {
            isRepresent = !isRepresent
        }
        RepresentRuleItem(
            rule = RepresentRuleUiModel.from(
                Rule(isRepresent = isRepresent)
            ),
            onClick = onClick
        )
    }
}
