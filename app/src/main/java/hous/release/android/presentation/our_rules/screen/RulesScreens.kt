package hous.release.android.presentation.our_rules.screen

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class RulesScreens(val route: String) {
    object Main : RulesScreens("main")
    object ADD : RulesScreens("add")
    object Delete : RulesScreens("delete/{$RULE_ID_KEY}") {
        val arguments = listOf(navArgument(RULE_ID_KEY) { type = NavType.IntType })
        fun createRouteBy(id: Int): String = "delete/$id"
    }

    object Update : RulesScreens("update/{$RULE_ID_KEY}") {
        val arguments = listOf(navArgument(RULE_ID_KEY) { type = NavType.IntType })
        fun createRouteBy(id: Int): String = "update/$id"
    }

    object Detail : RulesScreens("detail/{$RULE_ID_KEY}") {
        val arguments = listOf(navArgument(RULE_ID_KEY) { type = NavType.IntType })

        fun createRouteBy(id: Int): String = "detail/$id"
    }

    companion object {
        const val ROUTE = "rules_route"
        const val DETAIL_ROUTE = "rules_detail_route"
        const val RULE_ID_KEY = "id"
    }
}
