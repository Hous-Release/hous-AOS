package hous.release.android.presentation.todo

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.util.RoundedLinearIndicator

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

    val homieMessage = when (currentProgress) {
        0.0f -> stringResource(id = R.string.to_do_homie_message_notting)
        1.0f -> stringResource(id = R.string.to_do_homie_message_finish)
        else -> stringResource(id = R.string.to_do_homie_message_in_progress)
    }

    Column(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        MoveHomieWithMessage(
            progress = progress,
            homieMessage = homieMessage
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
    homieMessage: String
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .absoluteOffset(x = maxWidth.times(progress).plus(24.dp))
                .align(Alignment.CenterStart)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 12.dp
                    )
                )
                .background(Color.Gray)
                .padding(horizontal = 13.dp, vertical = 6.dp),
            text = homieMessage,
            color = Color.Black
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
