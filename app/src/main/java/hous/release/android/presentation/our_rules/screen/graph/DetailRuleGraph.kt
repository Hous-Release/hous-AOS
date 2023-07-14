package hous.release.android.presentation.our_rules.screen.graph

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import hous.release.android.presentation.our_rules.screen.DeleteRuleScreen
import hous.release.android.presentation.our_rules.screen.DetailRuleScreen
import hous.release.android.presentation.our_rules.screen.RulesScreens
import hous.release.android.presentation.our_rules.screen.UpdateRuleScreen
import timber.log.Timber

fun NavGraphBuilder.ruleDetailsGraph(
    navController: NavController
) {
    navigation(
        startDestination = RulesScreens.DetailGraph.Detail.route,
        route = RulesScreens.DetailGraph.route,
        arguments = RulesScreens.DetailGraph.arguments
    ) {
        detailRuleScreen(navController)
        updateRuleScreen(navController)
        deleteRuleScreen(navController)
    }
}

private fun NavGraphBuilder.detailRuleScreen(
    navController: NavController
) {
    composable(
        route = RulesScreens.DetailGraph.Detail.route
    ) { entry ->
        val id =
            remember(entry) {
                navController.getBackStackEntry(RulesScreens.DetailGraph.route).arguments?.getInt(
                    RulesScreens.RULE_ID_KEY
                )
            } ?: 0

        Timber.d("detailRuleScreen: $id")
        DetailRuleScreen(
            ruleId = id,
            onNavigateToUpdateRule = navController::navigateUpdateRule,
            onNavigateToDeleteRule = navController::navigateDeleteRule
        )
    }
}

private fun NavGraphBuilder.updateRuleScreen(
    navController: NavController
) {
    composable(
        RulesScreens.DetailGraph.Update.route
    ) { entry ->
        val id =
            remember(entry) {
                navController.getBackStackEntry(RulesScreens.DetailGraph.route).arguments?.getInt(
                    RulesScreens.RULE_ID_KEY
                )
            } ?: 0
        UpdateRuleScreen(ruleId = id)
    }
}

private fun NavGraphBuilder.deleteRuleScreen(
    navController: NavController
) {
    composable(
        RulesScreens.DetailGraph.Delete.route
    ) { entry ->
        val id =
            remember(entry) {
                navController.getBackStackEntry(RulesScreens.DetailGraph.route).arguments?.getInt(
                    RulesScreens.RULE_ID_KEY
                )
            } ?: 0
        DeleteRuleScreen(ruleId = id)
    }
}
