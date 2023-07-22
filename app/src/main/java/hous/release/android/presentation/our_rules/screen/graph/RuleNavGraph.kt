package hous.release.android.presentation.our_rules.screen.graph

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
import hous.release.android.presentation.our_rules.screen.AddRuleScreen
import hous.release.android.presentation.our_rules.screen.MainRuleScreen
import hous.release.android.presentation.our_rules.screen.RulesScreens
import hous.release.android.presentation.our_rules.viewmodel.MainRuleViewModel
import hous.release.android.presentation.practice.findActivity

@Composable
fun RuleNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RulesScreens.Main.route,
        route = RulesScreens.RULE_GRAPH_ROUTE
    ) {
        mainRuleScreen(
            onNavigateToAddRule = navController::navigateToAddRule,
            onNavigateToDetailRule = navController::navigateToDetailRuleGraph,
            onBack = navController::popBackStack
        )
        addRuleScreen()
        ruleDetailsGraph(navController)
    }
}

// Screens

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.mainRuleScreen(
    onNavigateToAddRule: () -> Unit,
    onNavigateToDetailRule: (Int) -> Unit,
    onBack: () -> Boolean
) {
    composable(RulesScreens.Main.route) {
        val activity = LocalContext.current.findActivity()
        val viewModel = hiltViewModel<MainRuleViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MainRuleScreen(
            mainRules = uiState.value.filteredRules,
            searchQuery = uiState.value.searchQuery,
            onSearch = viewModel::searchRule,
            onNavigateToAddRule = onNavigateToAddRule,
            onNavigateToDetailRule = onNavigateToDetailRule,
            onBack = onBack,
            onFinish = activity::finish
        )
    }
}

private fun NavGraphBuilder.addRuleScreen() {
    composable(RulesScreens.Add.route) {
        AddRuleScreen()
    }
}

// Navigation

fun NavController.navigateToAddRule() {
    navigate(RulesScreens.Add.route)
}

fun NavController.navigateToDetailRuleGraph(id: Int) {
    navigate(RulesScreens.DetailGraph.createRouteBy(id))
}

fun NavController.navigateUpdateRule() {
    navigate(RulesScreens.DetailGraph.Update.route)
}

fun NavController.navigateDeleteRule() {
    navigate(RulesScreens.DetailGraph.Delete.route)
}
