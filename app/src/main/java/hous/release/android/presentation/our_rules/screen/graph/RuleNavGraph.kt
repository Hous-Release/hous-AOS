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
        route = RulesScreens.ROUTE
    ) {
        mainRuleScreen(
            onNavigateToAddRule = navController::navigateToAddRule,
            onNavigateToDetailRule = navController::navigateToDetailRule
        )
        addRuleScreen()
        ruleDetailsGraph(
            onNavigateToUpdateRule = navController::navigateUpdateRule,
            onNavigateToDeleteRule = navController::navigateDeleteRule
        )
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
    composable(RulesScreens.ADD.route) {
        AddRuleScreen()
    }
}

// Navigation

fun NavController.navigateToAddRule() {
    navigate(RulesScreens.ADD.route)
}

fun NavController.navigateToDetailRule(id: Int) {
    navigate(RulesScreens.Detail.createRouteBy(id))
}

fun NavController.navigateUpdateRule(id: Int) {
    navigate(RulesScreens.Update.createRouteBy(id))
}

fun NavController.navigateDeleteRule(id: Int) {
    navigate(RulesScreens.Delete.createRouteBy(id))
}
