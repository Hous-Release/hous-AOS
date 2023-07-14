package hous.release.android.presentation.our_rules.screen.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hous.release.android.presentation.our_rules.screen.AddRuleScreen
import hous.release.android.presentation.our_rules.screen.MainRuleScreen
import hous.release.android.presentation.our_rules.screen.RulesScreens

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
            onNavigateToDetailRule = navController::navigateToDetailRuleGraph
        )
        addRuleScreen()
        ruleDetailsGraph(navController)
    }
}

// Screens

private fun NavGraphBuilder.mainRuleScreen(
    onNavigateToAddRule: () -> Unit,
    onNavigateToDetailRule: (Int) -> Unit
) {
    composable(RulesScreens.Main.route) {
        MainRuleScreen(
            onNavigateToAddRule = onNavigateToAddRule,
            onNavigateToDetailRule = onNavigateToDetailRule
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
