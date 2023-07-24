package hous.release.android.presentation.our_rules.screen.type

sealed class RulesScreens(val route: String) {
    object Main : RulesScreens("main")
    object Add : RulesScreens("add")

    object Update : RulesScreens("update")

    companion object {
        private const val DETAIL_RULE_GRAPH_ROUTE = "detail_rules_graph"
        const val RULE_GRAPH_ROUTE = "rules"
        const val DETAIL_RULE_KEY = "id"
    }
}
