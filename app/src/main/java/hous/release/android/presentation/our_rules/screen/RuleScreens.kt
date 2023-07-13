package hous.release.android.presentation.our_rules.screen

sealed class RuleScreens(val route: String) {
    object Main : RuleScreens("main")
    object ADD : RuleScreens("add")
    object Update : RuleScreens("update")
    object Delete : RuleScreens("delete")

    companion object {
        const val ROUTE = "our_rule"
    }
}
