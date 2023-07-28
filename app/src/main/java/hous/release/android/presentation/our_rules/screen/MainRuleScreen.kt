package hous.release.android.presentation.our_rules.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.presentation.our_rules.component.main.DetailRuleBottomSheetContent
import hous.release.android.presentation.our_rules.component.main.MainRuleContent
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite
import hous.release.domain.entity.rule.MainRule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainRuleScreen(
    detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    mainRules: List<MainRule> = emptyList(),
    searchQuery: String = "",
    fetchDetailRuleById: (Int) -> Unit = {},
    deleteAllPhotos: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onNavigateToUpdateRule: (DetailRuleUiModel) -> Unit = {},
    onNavigateToAddRule: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    BackHandler(enabled = bottomSheetState.isVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = bottomSheetState.isVisible.not()) {
        deleteAllPhotos()
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
                MainRule().copy(id = 1, name = "test1", isNew = true),
                MainRule().copy(id = 2, name = "test2", isNew = false),
                MainRule().copy(id = 3, name = "test3", isNew = true),
                MainRule().copy(id = 4, name = "test4", isNew = false)
            )
        )
    }
}
