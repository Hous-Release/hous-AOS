package hous.release.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HousCheckBox(
    modifier: Modifier = CheckBoxModifier,
    shape: Shape = CircleShape,
    backgroundColor: Color = HousBlue,
    borderColor: Color = HousBlueL1,
    enabled: Boolean = true,
    isChecked: Boolean = false,
    onCheckedChange: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indicator: Indication = rememberRipple(),
    content: (@Composable () -> Unit)? = null
) {
    val content: @Composable () -> Unit = {
        if (content != null) {
            content()
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                tint = HousWhite,
                contentDescription = "",
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = indicator,
                    enabled = enabled,
                    role = Role.Checkbox,
                    onClick = onCheckedChange
                )
            )
        }
    }
    Surface(
        enabled = enabled,
        onClick = onCheckedChange,
        modifier = modifier,
        shape = shape,
        color = if (isChecked) backgroundColor else HousWhite,
        border = if (isChecked) null else BorderStroke(1.dp, borderColor),
        interactionSource = interactionSource
    ) {
        if (isChecked) {
            content()
        }
    }
}

@Preview("HousCheckBox - Rule UseCase", showBackground = true)
@Composable
private fun PreviewRuleCheckBoxUsecase() {
    HousTheme {
        var isChecked by remember { mutableStateOf(true) }
        val onCheckedChange: () -> Unit = { isChecked = !isChecked }
        HousCheckBox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview("HousCheckBox - Rule UseCase2", showBackground = true)
@Composable
private fun PreviewRuleCheckBoxUsecase2() {
    HousTheme {
        var isChecked by remember { mutableStateOf(true) }
        val onCheckedChange: () -> Unit = { isChecked = !isChecked }
        HousCheckBox(
            modifier = Modifier.size(32.dp).padding(4.dp),
            shape = RoundedCornerShape(3.dp),
            backgroundColor = HousRed,
            borderColor = HousRed,
            isChecked = isChecked,
            onCheckedChange = onCheckedChange
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                tint = HousWhite,
                contentDescription = ""
            )
        }
    }
}

@Preview("HousCheckBox - Rule UseCase3", showBackground = true)
@Composable
private fun PreviewRuleCheckBoxUsecase3() {
    HousTheme {
        var isChecked by remember { mutableStateOf(true) }
        val onCheckedChange: () -> Unit = { isChecked = !isChecked }
        HousCheckBox(
            backgroundColor = HousWhite,
            borderColor = HousRed,
            isChecked = isChecked,
            onCheckedChange = onCheckedChange
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, HousRed, CircleShape)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(HousRed)
            )
        }
    }
}

private val CheckBoxModifier = Modifier
    .size(24.dp)
    .padding(2.dp)
