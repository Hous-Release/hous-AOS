package hous.release.android.presentation.our_rules.component.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousTheme

@Composable
fun RuleTitleGuide(
    name: String = stringResource(R.string.our_rule_add_edit_title),
    isEssential: Boolean = true
) {
    Text(
        modifier = Modifier.padding(bottom = 4.dp),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.b3.fontFamily,
                    fontWeight = HousTheme.typography.b3.fontWeight,
                    fontSize = HousTheme.typography.b3.fontSize,
                    color = HousBlack
                )
            ) {
                append(name)
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.b3.fontFamily,
                    fontWeight = HousTheme.typography.b3.fontWeight,
                    fontSize = HousTheme.typography.b3.fontSize,
                    color = HousRed
                )
            ) {
                if (isEssential) append(stringResource(R.string.todo_star))
            }
        }
    )
}

@Preview(name = "필수 기입해야한다는 것을 의미하는 Guide Title", showBackground = true)
@Composable
private fun RuleTitleGuidePreview() {
    HousTheme {
        RuleTitleGuide()
    }
}

@Preview(name = "선택적으로 기입해야한다는 것을 의미하는 Guide Title", showBackground = true)
@Composable
private fun RuleTitleGuidePreview2() {
    HousTheme {
        RuleTitleGuide("설명", isEssential = false)
    }
}
