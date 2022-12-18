package hous.release.android.presentation.our_rules

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.util.HousLogEvent.SCREEN_RULES
import hous.release.android.util.HousLogEvent.enterScreenLogEvent

@AndroidEntryPoint
class OurRulesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_rules)
        enterScreenLogEvent(SCREEN_RULES, javaClass.name)
    }
}
