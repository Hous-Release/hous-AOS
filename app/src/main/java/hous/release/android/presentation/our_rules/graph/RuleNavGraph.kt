package hous.release.android.presentation.our_rules.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.presentation.our_rules.screen.AddRuleScreen
import hous.release.android.presentation.our_rules.screen.MainRuleScreen
import hous.release.android.presentation.our_rules.screen.UpdateRuleScreen
import hous.release.android.presentation.our_rules.screen.type.RulesScreens
import hous.release.android.presentation.our_rules.viewmodel.MainRuleViewModel
import hous.release.android.presentation.practice.findActivity
import timber.log.Timber

@Composable
fun RuleNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RulesScreens.Main.route,
        route = RulesScreens.RULE_GRAPH_ROUTE
    ) {
        mainRuleScreen(navController)
        addRuleScreen(onBack = navController::popBackStack)
        updateRuleScreen(navController)
    }
}

// Screens

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.mainRuleScreen(
    navController: NavController
) {
    composable(RulesScreens.Main.route) {
        val activity = LocalContext.current.findActivity()
        val viewModel = hiltViewModel<MainRuleViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MainRuleScreen(
            detailRule = uiState.value.detailRule,
            mainRules = uiState.value.filteredRules,
            searchQuery = uiState.value.searchQuery,
            fetchDetailRuleById = viewModel::fetchDetailRule,
            onSearch = viewModel::searchRule,
            onNavigateToAddRule = navController::navigateToAddRule,
            onNavigateToUpdateRule = navController::navigateUpdateRule,
            onBack = navController::popBackStack,
            onFinish = activity::finish
        )
    }
}

private fun NavGraphBuilder.updateRuleScreen(
    navController: NavController
) {
    composable(
        RulesScreens.Update.route
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.get<DetailRuleUiModel>(
            RulesScreens.DETAIL_RULE_KEY
        )?.let { detailRule ->
            UpdateRuleScreen(rule = detailRule, onBack = navController::popBackStack)
        } ?: run {
            Timber.e("DetailRuleUiModel is null")
        }
    }
}

private fun NavGraphBuilder.addRuleScreen(onBack: () -> Boolean) {
    composable(RulesScreens.Add.route) {
        AddRuleScreen(
            onBack = onBack
        )
    }
}

// Navigation

fun NavController.navigateToAddRule() {
    navigate(RulesScreens.Add.route)
}

fun NavController.navigateUpdateRule(detailRuleUiModel: DetailRuleUiModel) {
    currentBackStackEntry?.savedStateHandle?.set(
        RulesScreens.DETAIL_RULE_KEY,
        detailRuleUiModel
    )
    navigate(RulesScreens.Update.route)
}
