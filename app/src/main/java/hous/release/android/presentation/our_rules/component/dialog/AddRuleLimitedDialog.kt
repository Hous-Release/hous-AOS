package hous.release.android.presentation.our_rules.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hous.release.android.R
import hous.release.designsystem.component.HousLimitDialog

@Composable
fun AddRuleLimitedDialog(
    onDismissRequest: () -> Unit
) {
    HousLimitDialog(
        title = stringResource(R.string.our_rule_limit_title),
        content = stringResource(R.string.our_rule_limit_content),
        onDismissRequest = onDismissRequest
    )
}
