package hous.release.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL2
import hous.release.designsystem.theme.HousG1
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme

enum class HousTextFieldMode {
    SEARCH,
    RIGHT_LIMITED,
    BOTTOM_END
}

@Composable
@Deprecated(
    "Preview 를 보시고 HousTextField 함수를 사용하시면 됩니다.",
    replaceWith = ReplaceWith("HousTextField")
)
fun HousSearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    localFocusManager: FocusManager = LocalFocusManager.current,
    onTextChange: (String) -> Unit
) {
    HousTextField(
        textFielddMode = HousTextFieldMode.SEARCH,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(HousBlueL2)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        text = text,
        hint = hint,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        onTextChange = onTextChange
    )
}

@Composable
fun HousTextField(
    textFielddMode: HousTextFieldMode,
    modifier: Modifier = Modifier,
    text: String,
    hint: String = "",
    limitTextCount: Int = 20,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onTextChange: (String) -> Unit
) {
    var backgroundColor by remember { mutableStateOf(HousG1) }
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .onFocusChanged { focus ->
                backgroundColor = if (focus.isFocused) HousBlueL2 else HousG1
            }
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        value = text,
        onValueChange = { innerText ->
            if (innerText.length <= limitTextCount) onTextChange(innerText)
        },
        textStyle = HousTheme.typography.b2,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(HousBlue)
    ) { innerTextField ->
        when (textFielddMode) {
            HousTextFieldMode.SEARCH -> {
                HousSearchTextField(
                    text = text,
                    hint = hint,
                    innerTextField = innerTextField
                )
            }

            HousTextFieldMode.RIGHT_LIMITED -> {
                HousRightLimitedTextField(
                    text = text,
                    limitTextCount = limitTextCount,
                    innerTextField = innerTextField
                )
            }

            HousTextFieldMode.BOTTOM_END -> {
                HousBottomEndLimitedTextField(
                    text = text,
                    limitTextCount = limitTextCount,
                    innerTextField = innerTextField
                )
            }
        }
    }
}

@Composable
private fun HousSearchTextField(
    text: String,
    hint: String,
    innerTextField: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null
        )
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    color = HousG5,
                    style = HousTheme.typography.b2
                )
            }
            innerTextField()
        }
    }
}

@Composable
private fun HousRightLimitedTextField(
    text: String,
    limitTextCount: Int,
    innerTextField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        innerTextField()
        Text(
            text = String.format(
                stringResource(id = R.string.text_field_limit_count),
                text.length,
                limitTextCount
            ),
            style = HousTheme.typography.en2,
            color = HousG5
        )
    }
}

@Composable
private fun HousBottomEndLimitedTextField(
    text: String,
    limitTextCount: Int,
    innerTextField: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.height(136.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        innerTextField()
        Text(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(),
            text = String.format(
                stringResource(id = R.string.text_field_limit_count),
                text.length,
                limitTextCount
            ),
            textAlign = TextAlign.End,
            style = HousTheme.typography.en2,
            color = HousG5
        )
    }
}

@Preview(name = "검색 text field")
@Composable
private fun TextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousTextField(
            textFielddMode = HousTextFieldMode.SEARCH,
            text = text,
            onTextChange = onChange,
            hint = "검색하기",
            modifier = Modifier
        )
    }
}

@Preview(name = "우측에 제한이 있는 text field")
@Composable
private fun HousRightLimitedTextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousTextField(
            textFielddMode = HousTextFieldMode.RIGHT_LIMITED,
            modifier = Modifier,
            text = text,
            onTextChange = onChange
        )
    }
}

@Preview(name = "우측 아래에 제한이 있는 text field")
@Composable
private fun HousBottomEndLimitedTextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousTextField(
            textFielddMode = HousTextFieldMode.BOTTOM_END,
            limitTextCount = 50,
            modifier = Modifier,
            text = text,
            onTextChange = onChange
        )
    }
}
