package hous.release.android.util.component

import androidx.annotation.ColorRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import kotlin.math.cos
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun HousPersonalityPentagon() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            HousPentagonText()
            HousPentagon(
                radiusList = listOf(350.0f, 350.0f, 350.0f, 350.0f, 350.0f),
                duration = 2000,
                backgroundColor = R.color.hous_blue_l1
            )
            HousPentagon(
                radiusList = listOf(180.0f, 300.0f, 50.0f, 300.0f, 100.0f),
                duration = 6000,
                backgroundColor = R.color.hous_blue
            )
        }
    }
}

enum class AniState {
    START, END
}

@Composable
fun HousPentagon(
    radiusList: List<Float>,
    duration: Int,
    @ColorRes backgroundColor: Int
) {
    val animationTargetState = remember { mutableStateOf(AniState.START) }
    val transition = updateTransition(
        targetState = animationTargetState.value,
        label = ""
    )
    val radiusAnimationSpec = transition.animateFloat(
        transitionSpec = { tween(durationMillis = duration) },
        label = ""
    ) { state -> if (state == AniState.START) 0f else 1f }
    val pentagonColor = colorResource(id = backgroundColor)

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(drawPentagonPath(radiusList, radiusAnimationSpec)),
                paint = Paint().apply {
                    color = pentagonColor
                    pathEffect = PathEffect.cornerPathEffect(15.0.dp.toPx())
                }
            )
        }
        animationTargetState.value = AniState.END
    }
}

private fun DrawScope.drawPentagonPath(
    radiusList: List<Float>,
    radiusAnimationSpec: State<Float>
): Path {
    val size = this.size.center
    val angle = 2.0 * Math.PI / 5.0f
    val radiusPxList = radiusList.onEach { radius -> radius.dp.toPx() }
    return Path().apply {
        val currentAngle = -0.5 * Math.PI
        reset()
        moveTo(
            size.x + (radiusPxList[0] * cos(currentAngle)).toFloat()
                .times(radiusAnimationSpec.value),
            size.y + (radiusPxList[0] * sin(currentAngle)).toFloat()
                .times(radiusAnimationSpec.value)
        )
        for (i in 1 until 5) {
            lineTo(
                size.x + (radiusPxList[i] * cos(currentAngle + angle * i))
                    .toFloat()
                    .times(radiusAnimationSpec.value),
                size.y + (radiusPxList[i] * sin(currentAngle + angle * i))
                    .toFloat()
                    .times(radiusAnimationSpec.value)
            )
        }
        close()
    }
}

@Composable
private fun HousPentagonText() {
    val textList = stringArrayResource(id = R.array.pentagon_text)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val currentAngle = -0.5 * Math.PI
        val angle = 2.0 * Math.PI / 5.0f
        val radiusList = listOf(340.0f, 350.0f, 350.0f, 350.0f, 350.0f)
        with(LocalDensity.current) {
            val radiusPxList = radiusList.onEach { radius -> radius.dp.toPx() }
            val center = Offset(
                this@BoxWithConstraints.maxWidth.toPx() / 2f,
                this@BoxWithConstraints.maxHeight.toPx() / 2f
            )
            for (i in 0 until 5) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = textList[i],
                        modifier = Modifier
                            .offset(
                                (center.x + (radiusPxList[i] * cos(currentAngle + angle * i)).toFloat() - 35).toDp(),
                                (center.y + (radiusPxList[i] * sin(currentAngle + angle * i)).toFloat() - 20).toDp()
                            ),
                        fontStyle = FontStyle(R.style.Description),
                        color = colorResource(id = R.color.hous_g_5),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .offset((center.x).dp, (center.y + 400.0f.dp.toPx()).dp)
                    .size(16.dp)
            )
        }
    }
}
