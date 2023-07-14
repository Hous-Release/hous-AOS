package hous.release.android.presentation.our_rules.screen.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hous.release.android.presentation.our_rules.screen.RulesScreens

fun NavGraphBuilder.ruleDetailsGraph(
    onNavigateToUpdateRule: (Int) -> Unit,
    onNavigateToDeleteRule: (Int) -> Unit
) {
    navigation(startDestination = RulesScreens.Detail.route, route = RulesScreens.DETAIL_ROUTE) {
        detailRuleScreen(
            onNavigateToUpdateRule = { onNavigateToUpdateRule(it) },
            onNavigateToDeleteRule = { onNavigateToDeleteRule(it) }
        )
        updateRuleScreen()
        deleteRuleScreen()
    }
}

private fun NavGraphBuilder.detailRuleScreen(
    onNavigateToUpdateRule: (Int) -> Unit,
    onNavigateToDeleteRule: (Int) -> Unit
) {
    composable(RulesScreens.Detail.route) {}
}

private fun NavGraphBuilder.updateRuleScreen() {
    composable(RulesScreens.Update.route) {}
}

private fun NavGraphBuilder.deleteRuleScreen() {
    composable(RulesScreens.Delete.route) {}
}
