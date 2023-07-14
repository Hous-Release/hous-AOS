package hous.release.android.presentation.our_rules.screen

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class RulesScreens(val route: String) {
    object Main : RulesScreens("main")
    object Add : RulesScreens("add")

    object DetailGraph : RulesScreens("$DETAIL_RULE_GRAPH_ROUTE/{$RULE_ID_KEY}") {
        val arguments = listOf(navArgument(RULE_ID_KEY) { type = NavType.IntType })
        fun createRouteBy(id: Int): String = this.route.replace("{$RULE_ID_KEY}", id.toString())

        object Delete : RulesScreens("delete")

        object Update : RulesScreens("update")

        object Detail : RulesScreens("detail")
    }

    companion object {
        private const val DETAIL_RULE_GRAPH_ROUTE = "detail_rules_graph"
        const val RULE_GRAPH_ROUTE = "rules"
        const val RULE_ID_KEY = "id"
    }
}
