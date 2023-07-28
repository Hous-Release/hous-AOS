package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG2
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme

private val MinMenuWidth = 139.dp
private val DropdownMenuItemContentStartPadding = 20.dp
private val DropdownDividerPadding = 14.dp

@Composable
fun MainRuleDropDownMenu(
    isExpanded: Boolean = false,
    onDismiss: () -> Unit,
    onNavigateToRepresentation: () -> Unit = {},
    onNavigateToGuide: () -> Unit = {}
) {
    // device width dp
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    // dropdown menu offset

    val dropDownOffset = DpOffset(x = screenWidth - MinMenuWidth - 32.dp, y = (-8).dp)

    DropdownMenu(
        modifier = Modifier
            .wrapContentSize()
            .sizeIn(minWidth = MinMenuWidth),
        offset = dropDownOffset,
        expanded = isExpanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            onClick = onNavigateToRepresentation,
            contentPadding = PaddingValues(start = DropdownMenuItemContentStartPadding)
        ) {
            Text(
                text = stringResource(id = R.string.our_rule_menu_edit_representation),
                style = HousTheme.typography.b2,
                color = HousBlue,
                modifier = Modifier.wrapContentSize()
            )
        }
        Divider(
            color = HousG2,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = DropdownDividerPadding)
        )
        DropdownMenuItem(
            onClick = onNavigateToGuide,
            contentPadding = PaddingValues(start = DropdownMenuItemContentStartPadding)
        ) {
            Text(
                text = stringResource(id = R.string.our_rule_menu_edit_guide),
                style = HousTheme.typography.b2,
                color = HousG6,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Preview(name = "MainRuleDropDown", showBackground = true)
@Composable
fun PreviewMainRuleDropDown() {
    HousTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var expanded by remember { mutableStateOf(false) }
            val onClick = { expanded = !expanded }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.TopEnd)
            ) {
                Button(onClick = onClick) {
                    Text(text = "Click me")
                }
                MainRuleDropDownMenu(
                    expanded,
                    { expanded = false }
                )
            }
            var expanded2 by remember { mutableStateOf(false) }
            val onClick2 = { expanded2 = !expanded2 }
            Box(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Button(onClick = onClick2) {
                    Text(text = "Click me")
                }
                MainRuleDropDownMenu(
                    expanded2,
                    { expanded2 = false }
                )
            }
        }
    }
}
