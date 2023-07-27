package hous.release.android.presentation.our_rules.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hous.release.designsystem.theme.HousTheme

@Composable
fun DetailRuleScreen(
    ruleId: Int = 0,
    onNavigateToUpdateRule: () -> Unit = {},
    onNavigateToDeleteRule: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detail Rule $ruleId",
            style = HousTheme.typography.h1
        )
        Button(onClick = onNavigateToUpdateRule) {
            Text(
                text = "Update Rule",
                style = HousTheme.typography.h1
            )
        }
        Button(onClick = onNavigateToDeleteRule) {
            Text(
                text = "Delete Rule",
                style = HousTheme.typography.h1
            )
        }
    }
}
