package hous.release.android.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R

@Composable
fun HousFloatingButton(
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.hous_blue))
            .clickable { onClick() }
            .size(60.dp),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.to_do_plus),
        color = colorResource(id = R.color.hous_white),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_medium)),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            lineHeight = 12.sp
        )
    )
}
