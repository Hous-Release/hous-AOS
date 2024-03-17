package hous.release.android.presentation.our_rules.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import hous.release.android.presentation.our_rules.component.dialog.DeleteRuleDialog
import hous.release.android.presentation.our_rules.component.main.DetailRuleBottomSheetContent
import hous.release.android.presentation.our_rules.component.main.MainRuleContent
import hous.release.android.presentation.our_rules.component.main.RuleGuideBottomSheetContent
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite
import hous.release.domain.entity.rule.Rule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainRuleScreen(
    detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    filteredRules: List<Rule> = emptyList(),
    originRules: List<Rule> = emptyList(),
    searchQuery: String = "",
    fetchDetailRuleById: (Int) -> Unit = {},
    deleteRule: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onNavigateToUpdateRule: (DetailRuleUiModel) -> Unit = {},
    onNavigateToAddRule: () -> Unit = {},
    onNavigateToRepresentRule: () -> Unit = {},
    onFinish: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    var isShowDeleteRuleDialog by remember { mutableStateOf(false) }
    var bottomSheetType: BottomSheetType by remember { mutableStateOf(BottomSheetType.DetailRule) }

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
    if (isShowDeleteRuleDialog) {
        DeleteRuleDialog(
            onConfirm = {
                coroutineScope.launch {
                    deleteRule()
                    isShowDeleteRuleDialog = false
                    bottomSheetState.hide()
                }
            },
            onDismiss = { isShowDeleteRuleDialog = false }
        )
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            if (bottomSheetType is BottomSheetType.DetailRule) {
                DetailRuleBottomSheetContent(
                    detailRule = detailRule,
                    onNavigateToUpdateRule = { detailRule ->
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            onNavigateToUpdateRule(detailRule)
                        }
                    },
                    onDeleteRule = { isShowDeleteRuleDialog = true }
                )
            }
            if (bottomSheetType is BottomSheetType.RuleGuide) {
                RuleGuideBottomSheetContent()
            }
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetElevation = 8.dp,
        sheetBackgroundColor = HousWhite
    ) {
        MainRuleContent(
            filteredRules = filteredRules,
            originRules = originRules,
            searchQuery = searchQuery,
            onSearch = onSearch,
            onOpenDetailRule = { id ->
                coroutineScope.launch {
                    fetchDetailRuleById(id)
                    bottomSheetType = BottomSheetType.DetailRule
                    bottomSheetState.show()
                }
            },
            onOpenRuleGuide = {
                coroutineScope.launch {
                    bottomSheetType = BottomSheetType.RuleGuide
                    bottomSheetState.show()
                }
            },
            onNavigateToAddRule = onNavigateToAddRule,
            onNavigateToRepresentRule = onNavigateToRepresentRule,
            onFinish = onFinish
        )
    }
}

@Preview(name = "MainRuleScreen", showBackground = true)
@Composable
private fun MainRuleScreenPreView2() {
    HousTheme {
        MainRuleScreen(
            filteredRules = listOf(
                Rule().copy(id = 1, name = "test1", isNew = true),
                Rule().copy(id = 2, name = "test2", isNew = false),
                Rule().copy(id = 3, name = "test3", isNew = true),
                Rule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}

sealed class BottomSheetType {
    object DetailRule : BottomSheetType()
    object RuleGuide : BottomSheetType()
}
