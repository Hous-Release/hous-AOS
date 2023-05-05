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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL2
import hous.release.designsystem.theme.HousG1
import hous.release.designsystem.theme.HousG5
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousBottomLimitedTextField(
    modifier: Modifier,
    text: String,
    color: Color,
    limitTextCount: Int,
    height: Dp,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        HousTextField(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(1f),
            text = text,
            onTextChange = { innerText ->
                if (innerText.length <= limitTextCount) onTextChange(innerText)
            }
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(),
            text = "${text.length}/$limitTextCount",
            textAlign = TextAlign.End,
            style = HousTheme.typography.en2,
            color = HousG5
        )
    }
}

@Composable
fun HousLeftLimitedTextField(
    modifier: Modifier,
    text: String,
    color: Color,
    limitTextCount: Int,
    localFocusManager: FocusManager = LocalFocusManager.current,
    onTextChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 16.dp, vertical = 11.dp)
    ) {
        HousTextField(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(1f),
            text = text,
            onTextChange = { innerText ->
                if (innerText.length <= limitTextCount) onTextChange(innerText)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
        )
        Text(
            text = "${text.length}/$limitTextCount",
            style = HousTheme.typography.en2,
            color = HousG5
        )
    }
}

@Composable
fun HousSearchTextField(
    modifier: Modifier,
    text: String,
    hint: String,
    localFocusManager: FocusManager = LocalFocusManager.current,
    onTextChange: (String) -> Unit
) {
    HousTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(HousBlueL2)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        text = text,
        hint = hint,
        isPrefixIcon = true,
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
private fun HousTextField(
    modifier: Modifier,
    text: String,
    hint: String = "",
    isPrefixIcon: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = { onTextChange(it) },
        textStyle = HousTheme.typography.b2,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(HousBlue)
    ) { innerTextField ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isPrefixIcon) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = if (isPrefixIcon) Alignment.CenterStart else Alignment.TopStart
            ) {
                if (hint.isNotBlank()) {
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
}

@Preview(name = "검색 text field")
@Composable
private fun DescriptionTextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousSearchTextField(
            text = text,
            onTextChange = onChange,
            hint = "검색하기",
            modifier = Modifier,
        )
    }
}

@Preview(name = "왼쪽에 제한이 있는 text field")
@Composable
private fun HousLeftLimitedTextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousLeftLimitedTextField(
            modifier = Modifier,
            text = text,
            onTextChange = onChange,
            color = HousG1,
            limitTextCount = 20
        )
    }
}

@Preview(name = "아래쪽에 제한이 있는 text field")
@Composable
private fun HousBottomLimitedTextFieldPreview() {
    HousTheme {
        var text by remember { mutableStateOf("") }
        val onChange: (String) -> Unit = { text = it }
        HousBottomLimitedTextField(
            modifier = Modifier,
            text = text,
            onTextChange = onChange,
            color = HousBlueL2,
            height = 136.dp,
            limitTextCount = 50
        )
    }
}
