package hous.release.android.presentation.our_rules.screen

sealed class RulesScreens(val route: String) {
    object Main : RulesScreens("main")
    object ADD : RulesScreens("add")
    object Delete : RulesScreens("delete/{$RULE_ID_KEY}}") {
        fun createRouteBy(id: Int): String = "detail/$id"
    }

    object Update : RulesScreens("update/{$RULE_ID_KEY}}") {
        fun createRouteBy(id: Int): String = "detail/$id"
    }

    object Detail : RulesScreens("detail/{$RULE_ID_KEY}}") {
        fun createRouteBy(id: Int): String = "detail/$id"
    }

    companion object {
        const val ROUTE = "rules_route"
        const val DETAIL_ROUTE = "rules_detail_route"
        private const val RULE_ID_KEY = "id"
    }
}
