package hous.release.android.presentation.our_rules

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.presentation.our_rules.graph.RuleNavGraph
import hous.release.android.util.HousLogEvent.SCREEN_RULES
import hous.release.android.util.HousLogEvent.enterScreenLogEvent
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class OurRulesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterScreenLogEvent(SCREEN_RULES, javaClass.name)
        setContent {
            val navController = rememberNavController()
            HousTheme {
                RuleNavGraph(navController)
            }
        }
    }
}
