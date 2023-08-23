package hous.release.android.presentation.our_rules.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hous.release.designsystem.theme.HousTheme

@Composable
fun DeleteRuleScreen(
    ruleId: Int = 0
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Delete Rule $ruleId",
            style = HousTheme.typography.h1
        )
    }
}