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
import hous.release.android.presentation.our_rules.screen.AddRuleScreen
import hous.release.android.presentation.our_rules.screen.MainRuleScreen
import hous.release.android.presentation.our_rules.screen.UpdateRuleScreen
import hous.release.android.presentation.our_rules.screen.type.RulesScreens
import hous.release.android.presentation.our_rules.viewmodel.MainRuleViewModel
import hous.release.android.presentation.practice.findActivity
import hous.release.domain.entity.rule.DetailRule
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
        mainRuleScreen(
            onNavigateToAddRule = navController::navigateToAddRule,
            onNavigateToUpdateRule = navController::navigateUpdateRule,
            onBack = navController::popBackStack
        )
        addRuleScreen(onBack = navController::popBackStack)
        updateRuleScreen(onBack = navController::popBackStack)
    }
}

// Screens

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun NavGraphBuilder.mainRuleScreen(
    onNavigateToAddRule: () -> Unit,
    onNavigateToUpdateRule: (Int) -> Unit,
    onBack: () -> Boolean
) {
    composable(RulesScreens.Main.route) {
        val activity = LocalContext.current.findActivity()
        val viewModel = hiltViewModel<MainRuleViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MainRuleScreen(
            detailRule = DetailRule(), // TODO Change to uiState.value.detailRule
            mainRules = uiState.value.filteredRules,
            searchQuery = uiState.value.searchQuery,
            onSearch = viewModel::searchRule,
            onNavigateToAddRule = onNavigateToAddRule,
            onNavigateToUpdateRule = onNavigateToUpdateRule,
            onBack = onBack,
            onFinish = activity::finish
        )
    }
}

private fun NavGraphBuilder.updateRuleScreen(onBack: () -> Boolean) {
    composable(
        RulesScreens.Update.route,
        arguments = RulesScreens.Update.arguments
    ) { entry ->
        entry.arguments?.getInt(RulesScreens.RULE_ID_KEY)?.let {
            UpdateRuleScreen(ruleId = id, onBack = onBack)
        } ?: run {
            Timber.e("Rule id is null")
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

fun NavController.navigateUpdateRule(id: Int) {
    navigate(RulesScreens.Update.createRouteBy(id))
}
