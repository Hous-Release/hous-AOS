package hous.release.android.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun RoundedLinearIndicator(
    modifier: Modifier,
    progress: Float,
    trackColor: Color,
    backgroundColor: Color
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        val calculatedProgress = (progress * size.width)

        drawRoundedLinearIndicator(
            color = backgroundColor,
            endXOffset = size.width,
            strokeWidth = 30f
        )

        drawRoundedLinearIndicator(
            color = trackColor,
            endXOffset = calculatedProgress,
            strokeWidth = 30f
        )
    }
}

private fun DrawScope.drawRoundedLinearIndicator(
    color: Color,
    endXOffset: Float,
    strokeWidth: Float
) {
    drawLine(
        color = color,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = endXOffset, y = 0f),
        cap = StrokeCap.Round,
        strokeWidth = strokeWidth
    )
}
