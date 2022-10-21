package hous.release.android.presentation.todo.main

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R
import hous.release.android.util.component.LEFT
import hous.release.android.util.component.RIGHT
import hous.release.android.util.component.RoundedLinearIndicator
import hous.release.android.util.component.drawMessageShape

@Composable
fun RoundedLinearIndicatorWithHomie(
    currentProgress: Float
) {
    val progress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(
            durationMillis = 600,
            easing = LinearOutSlowInEasing
        )
    )

    val messagePosition = if (currentProgress > 0.5f) LEFT else RIGHT

    val homieMessage = when (currentProgress) {
        0.0f -> stringResource(id = R.string.to_do_homie_message_notting)
        1.0f -> stringResource(id = R.string.to_do_homie_message_finish)
        else -> stringResource(id = R.string.to_do_homie_message_in_progress)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        MoveHomieWithMessage(
            progress = progress,
            homieMessage = homieMessage,
            messagePosition = messagePosition
        )
        Spacer(modifier = Modifier.height(12.dp))
        RoundedLinearIndicator(
            progress = progress,
            trackColor = colorResource(id = R.color.hous_blue),
            backgroundColor = colorResource(id = R.color.hous_blue_l2),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}

@Composable
private fun MoveHomieWithMessage(
    progress: Float,
    homieMessage: String,
    messagePosition: Int
) {
    val messageColor = colorResource(id = R.color.hous_g_2)
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .absoluteOffset(
                    x = maxWidth
                        .times(progress)
                        .plus(if (messagePosition == RIGHT) 24.dp else (-144).dp)
                )
                .align(Alignment.CenterStart)
                .drawBehind {
                    drawMessageShape(
                        side = 14.dp,
                        firstX = 10.dp,
                        secondX = 20.dp,
                        cornerRadius = 15.dp,
                        messageColor = messageColor,
                        direction = messagePosition
                    )
                }
                .padding(
                    start = if (messagePosition == RIGHT) 24.dp else 12.dp,
                    end = if (messagePosition == RIGHT) 12.dp else 24.dp,
                    top = 6.dp,
                    bottom = 5.dp
                ),
            text = homieMessage,
            color = colorResource(id = R.color.hous_g_5),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_medium)),
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                letterSpacing = (-0.02).sp,
                lineHeight = 6.5.sp
            )
        )
        Image(
            modifier = Modifier.absoluteOffset(x = maxWidth.times(progress).minus(24.dp)),
            painter = painterResource(id = R.drawable.ic_to_do_temp_homie),
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoundedLinearIndicatorWithHomiePreview() {
    RoundedLinearIndicatorWithHomie(0.3f)
}
