package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.FabScreenSlot
import hous.release.designsystem.component.HousTextField
import hous.release.designsystem.component.HousTextFieldMode
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.Rule
import hous.release.feature.todo.R

@Composable
fun MainRuleContent(
    filteredRules: List<Rule> = emptyList(),
    originRules: List<Rule> = emptyList(),
    searchQuery: String = "",
    onSearch: (String) -> Unit = {},
    onOpenDetailRule: (Int) -> Unit = {},
    onOpenRuleGuide: () -> Unit = {},
    onNavigateToAddRule: () -> Unit = {},
    onNavigateToRepresentRule: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    FabScreenSlot(
        fabOnClick = onNavigateToAddRule
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainRuleToolbar(
                onBack = onFinish,
                onOpenRuleGuide = onOpenRuleGuide,
                onNavigateToRepresentRule = onNavigateToRepresentRule
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            HousTextField(
                textFielddMode = HousTextFieldMode.SEARCH,
                text = searchQuery,
                onTextChange = onSearch,
                hint = "Rules 검색하기",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            MainRuleList(
                originRules = originRules,
                filteredRules = filteredRules,
                onNavigateToDetailRule = onOpenDetailRule
            )
        }
    }
}

@Preview(name = "MainRuleScreen", showBackground = true)
@Composable
private fun MainRuleScreenPreView2() {
    HousTheme {
        MainRuleContent(
            filteredRules = listOf(
                Rule().copy(id = 1, name = "test1", isNew = true),
                Rule().copy(id = 2, name = "test2", isNew = false),
                Rule().copy(id = 3, name = "test3", isNew = true),
                Rule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}

@Preview(name = "MainRuleScreen - empty Main Rules", showBackground = true)
@Composable
private fun MainRuleScreenPreView() {
    HousTheme {
        MainRuleContent()
    }
}
