package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme

@Composable
fun RuleEmptyContent(
    modifier: Modifier = Modifier.fillMaxWidth().padding(top = 88.dp)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.hous_empty_our_rules),
            color = HousG5,
            style = HousTheme.typography.b2
        )
    }
}

@Preview("RuleEmptyContent - RuleScreen에 Rule이 없을 떄", showBackground = true)
@Composable
private fun Preview() {
    HousTheme {
        RuleEmptyContent()
    }
}
