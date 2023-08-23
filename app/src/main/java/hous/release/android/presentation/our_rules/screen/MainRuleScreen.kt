package hous.release.android.presentation.our_rules.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import hous.release.android.presentation.our_rules.component.main.DetailRuleBottomSheetContent
import hous.release.android.presentation.our_rules.component.main.MainRuleContent
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite
import hous.release.domain.entity.rule.Rule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainRuleScreen(
    detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    mainRules: List<Rule> = emptyList(),
    searchQuery: String = "",
    fetchDetailRuleById: (Int) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onNavigateToUpdateRule: (DetailRuleUiModel) -> Unit = {},
    onNavigateToAddRule: () -> Unit = {},
    onFinish: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                refresh()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            DetailRuleBottomSheetContent(
                detailRule = detailRule,
                onNavigateToUpdateRule = onNavigateToUpdateRule,
                onDeleteRule = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetElevation = 8.dp,
        sheetBackgroundColor = HousWhite
    ) {
        MainRuleContent(
            mainRules = mainRules,
            searchQuery = searchQuery,
            onSearch = onSearch,
            onOpenDetailRule = { id ->
                coroutineScope.launch {
                    fetchDetailRuleById(id)
                    bottomSheetState.show()
                }
            },
            onNavigateToAddRule = onNavigateToAddRule,
            onFinish = onFinish
        )
    }
}

@Preview(name = "MainRuleScreen", showBackground = true)
@Composable
private fun MainRuleScreenPreView2() {
    HousTheme {
        MainRuleScreen(
            mainRules = listOf(
                Rule().copy(id = 1, name = "test1", isNew = true),
                Rule().copy(id = 2, name = "test2", isNew = false),
                Rule().copy(id = 3, name = "test3", isNew = true),
                Rule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}
