package hous.release.android.presentation.our_rules.component.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import hous.release.android.R
import hous.release.designsystem.component.HousLimitDialog

@Composable
fun AddRuleLimitedDialog(
    enableRechecking: Boolean = false
) {
    var isOutDialogShow by remember { mutableStateOf(false) }

    BackHandler(enableRechecking) {
        isOutDialogShow = true
    }
    if (isOutDialogShow) {
        HousLimitDialog(
            title = stringResource(R.string.our_rule_limit_title),
            content = stringResource(R.string.our_rule_limit_content),
            onDismissRequest = {
                isOutDialogShow = false
            }
        )
    }
}
