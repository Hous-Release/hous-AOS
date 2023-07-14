package hous.release.android.presentation.our_rules.screen.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hous.release.android.presentation.our_rules.screen.DeleteRuleScreen
import hous.release.android.presentation.our_rules.screen.DetailRuleScreen
import hous.release.android.presentation.our_rules.screen.RulesScreens
import hous.release.android.presentation.our_rules.screen.UpdateRuleScreen

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
    composable(
        route = RulesScreens.Detail.route,
        arguments = RulesScreens.Detail.arguments
    ) {
        it.arguments?.getInt(RulesScreens.RULE_ID_KEY)?.let { ruleId ->
            DetailRuleScreen(
                ruleId = ruleId,
                onNavigateToUpdateRule = { onNavigateToUpdateRule(ruleId) },
                onNavigateToDeleteRule = { onNavigateToDeleteRule(ruleId) }
            )
        }
    }
}

private fun NavGraphBuilder.updateRuleScreen() {
    composable(
        RulesScreens.Update.route,
        arguments = RulesScreens.Detail.arguments
    ) {
        it.arguments?.getInt(RulesScreens.RULE_ID_KEY)?.let { ruleId ->
            UpdateRuleScreen(ruleId = ruleId)
        }
    }
}

private fun NavGraphBuilder.deleteRuleScreen() {
    composable(
        RulesScreens.Delete.route,
        arguments = RulesScreens.Detail.arguments
    ) {
        it.arguments?.getInt(RulesScreens.RULE_ID_KEY)?.let { ruleId ->
            DeleteRuleScreen(ruleId = ruleId)
        }
    }
}
