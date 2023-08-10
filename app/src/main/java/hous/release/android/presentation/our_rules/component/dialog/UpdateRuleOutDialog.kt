package hous.release.android.presentation.our_rules.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import hous.release.android.R
import hous.release.designsystem.component.HousDialog

@Composable
fun UpdateRuleOutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    HousDialog(
        title = stringResource(R.string.warning_edit_rule_title),
        content = stringResource(R.string.warning_edit_rule_desc),
        neutralText = stringResource(R.string.warning_edit_rule_cancel),
        actionText = stringResource(R.string.warning_edit_rule_confirm),
        actionOnClick = onConfirm,
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}
