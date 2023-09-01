package hous.release.android.presentation.our_rules.screen.type

sealed class RulesScreens(val route: String) {
    object Main : RulesScreens("main")
    object Add : RulesScreens("add")

    object Update : RulesScreens("update")

    object Represent : RulesScreens("represent")

    companion object {
        const val RULE_GRAPH_ROUTE = "rules"
        const val DETAIL_RULE_KEY = "id"
    }
}
