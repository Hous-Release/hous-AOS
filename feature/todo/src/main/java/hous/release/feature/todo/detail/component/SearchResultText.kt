package hous.release.feature.todo.detail.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousTheme
import hous.release.feature.todo.R

@Composable
fun SearchResultText(
    searchResultCnt: Int
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.description.fontFamily,
                    fontWeight = HousTheme.typography.description.fontWeight,
                    fontSize = HousTheme.typography.description.fontSize,
                    color = HousG4
                )
            ) {
                append(stringResource(R.string.filter_search_result))
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.b3.fontFamily,
                    fontWeight = HousTheme.typography.b3.fontWeight,
                    fontSize = HousTheme.typography.b3.fontSize,
                    color = HousBlue
                )
            ) {
                append(searchResultCnt.toString())
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = HousTheme.typography.description.fontFamily,
                    fontWeight = HousTheme.typography.description.fontWeight,
                    fontSize = HousTheme.typography.description.fontSize,
                    color = HousG4
                )
            ) {
                append(stringResource(R.string.filter_search_result_postfix))
            }
        },
        textAlign = TextAlign.Center
    )
}
