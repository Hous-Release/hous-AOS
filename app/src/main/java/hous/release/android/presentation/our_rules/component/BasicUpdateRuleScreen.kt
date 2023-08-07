package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.presentation.our_rules.component.add.RuleTitleGuide
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.designsystem.component.HousTextField
import hous.release.designsystem.component.HousTextFieldMode
import hous.release.designsystem.theme.HousTheme

@Composable
fun BasicUpdateRuleScreen(
    title: String,
    trailingTitle: String,
    ruleName: String = "",
    description: String = "",
    photos: List<PhotoUiModel> = emptyList(),
    addRule: () -> Unit = { },
    changeName: (String) -> Unit = {},
    changeDescription: (String) -> Unit = {},
    deletePhoto: (photo: PhotoUiModel) -> Unit = {},
    onOpenGallery: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Box {
        Column(
            modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
            horizontalAlignment = Alignment.Start
        ) {
            RuleToolbar(
                title = title,
                trailingTitle = trailingTitle,
                isButtonActive = ruleName.isNotBlank(),
                onBack = onBack,
                onAddButton = addRule
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            ) {
                RuleTitleGuide(
                    stringResource(R.string.our_rule_add_edit_title)
                )
                HousTextField(
                    textFielddMode = HousTextFieldMode.RIGHT_LIMITED,
                    text = ruleName,
                    onTextChange = changeName,
                    limitTextCount = 10,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                RuleTitleGuide(
                    stringResource(R.string.our_rule_add_edit_description),
                    isEssential = false
                )
                HousTextField(
                    textFielddMode = HousTextFieldMode.BOTTOM_END,
                    text = description,
                    onTextChange = changeDescription,
                    limitTextCount = 50,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                RuleTitleGuide(
                    stringResource(R.string.our_rule_add_edit_photo),
                    isEssential = false
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            RulePhotoStatusBar(
                photoCount = photos.size,
                onOpenGallery = onOpenGallery
            )
            Spacer(modifier = Modifier.height(16.dp))
            PhotoGrid(
                edgeWidthDp = 16.dp,
                spaceWidthDp = 12.dp,
                photoFraction = 0.416f,
                isEditable = true,
                photos = photos,
                onRemove = deletePhoto
            )
        }
    }
}

@Preview(name = "add rule", showBackground = true)
@Composable
private fun BasicUpdateRuleScreenPreview() {
    HousTheme {
        BasicUpdateRuleScreen(
            stringResource(id = R.string.our_rule_add_new_rule_title),
            stringResource(id = R.string.our_rule_add_new_rule)
        )
    }
}

@Preview(name = "edit rule", showBackground = true)
@Composable
private fun BasicUpdateRuleScreenPreview2() {
    HousTheme {
        BasicUpdateRuleScreen(
            stringResource(id = R.string.our_rule_edit_rule_title),
            stringResource(id = R.string.our_rule_save_new_rule)
        )
    }
}
