package hous.release.android.presentation.our_rules.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hous.release.android.R
import hous.release.android.presentation.our_rules.component.BasicUpdateRuleScreen
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.designsystem.theme.HousTheme

@Composable
fun UpdateRuleScreen(
    ruleName: String = "",
    description: String = "",
    photos: List<PhotoUiModel> = emptyList(),
    updateRule: () -> Unit = { },
    changeName: (String) -> Unit = {},
    changeDescription: (String) -> Unit = {},
    deletePhoto: (photo: PhotoUiModel) -> Unit = {},
    onOpenGallery: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    BasicUpdateRuleScreen(
        title = stringResource(id = R.string.our_rule_edit_rule_title),
        trailingTitle = stringResource(id = R.string.our_rule_save_new_rule),
        ruleName = ruleName,
        description = description,
        photos = photos,
        addRule = updateRule,
        changeName = changeName,
        changeDescription = changeDescription,
        deletePhoto = deletePhoto,
        onOpenGallery = onOpenGallery,
        onBack = onBack
    )
}

@Preview(showBackground = true)
@Composable
private fun UpdateRuleScreenPreview() {
    HousTheme {
        UpdateRuleScreen()
    }
}