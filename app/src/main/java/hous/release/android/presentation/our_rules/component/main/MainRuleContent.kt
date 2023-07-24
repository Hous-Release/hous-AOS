package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.component.FabScreenSlot
import hous.release.designsystem.component.HousSearchTextField
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.rule.MainRule
import hous.release.feature.todo.R

@Composable
fun MainRuleContent(
    mainRules: List<MainRule> = emptyList(),
    searchQuery: String = "",
    onSearch: (String) -> Unit = {},
    onOpenDetailRule: (Int) -> Unit = {},
    onNavigateToAddRule: () -> Unit = {},
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
                    end = 16.dp,
                    top = 20.dp
                ).pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainRuleToolbar(
                onBack = onFinish
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            HousSearchTextField(
                text = searchQuery,
                onTextChange = onSearch,
                hint = stringResource(R.string.todo_detail_textfield_hint)
            )
            MainRuleList(
                mainRules = mainRules,
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
            mainRules = listOf(
                MainRule().copy(id = 1, name = "test1", isNew = true),
                MainRule().copy(id = 2, name = "test2", isNew = false),
                MainRule().copy(id = 3, name = "test3", isNew = true),
                MainRule().copy(id = 4, name = "test4", isNew = false)
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
