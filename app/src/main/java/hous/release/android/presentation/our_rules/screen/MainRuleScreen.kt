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
fun MainRuleScreen(
    onNavigateToDetailRule: (Int) -> Unit = {},
    onNavigateToAddRule: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onNavigateToAddRule) {
            Text(
                text = "Add Rule",
                style = HousTheme.typography.h1
            )
        }
        Button(onClick = { onNavigateToDetailRule(1) }) {
            Text(
                text = "Detail Rule",
                style = HousTheme.typography.h1
            )
        }
    }
}
