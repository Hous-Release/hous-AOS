package hous.release.android.util.component

import androidx.annotation.ColorRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.util.style.HousTheme
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TestScore
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
private fun HousPersonalityPentagonPreview() {
    HousPersonalityPentagon(
        testScore = TestScore(5, 5, 5, 5, 5),
        homyType = HomyType.BLUE
    )
}

@Composable
fun HousPersonalityPentagon(
    testScore: TestScore,
    homyType: HomyType
) {
    val backgroundColor = when (homyType) {
        HomyType.YELLOW -> R.color.hous_yellow_b_1
        HomyType.RED -> R.color.hous_red_b_1
        HomyType.BLUE -> R.color.hous_blue_l1
        HomyType.PURPLE -> R.color.hous_purple_b_1
        HomyType.GREEN -> R.color.hous_green_b_1
        HomyType.GRAY -> R.color.hous_g_1
    }

    val pentagonColor = when (homyType) {
        HomyType.YELLOW -> R.color.hous_yellow
        HomyType.RED -> R.color.hous_red
        HomyType.BLUE -> R.color.hous_blue
        HomyType.PURPLE -> R.color.hous_purple
        HomyType.GREEN -> R.color.hous_green
        HomyType.GRAY -> R.color.hous_g_1
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            HousPentagonText()
            HousPentagon(
                testScore = TestScore(10, 10, 10, 10, 10),
                duration = 2000,
                backgroundColor = backgroundColor
            )
            HousPentagon(
                testScore = testScore,
                duration = 4000,
                backgroundColor = pentagonColor
            )
        }
    }
}

enum class AniState {
    START, END
}

@Composable
private fun HousPentagon(
    testScore: TestScore,
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
    val radiusFloatList = testScore.toFloatList()

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(drawPentagonPath(radiusFloatList, radiusAnimationSpec)),
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HousPentagonText() {
    val textList = stringArrayResource(id = R.array.pentagon_text)
    val xSite = listOf(-22, -40, -48, 0, -24)
    val ySite = listOf(-28, -24, -30, -30, -8)
    var textVisible by remember { mutableStateOf(false) }
    val currentAngle = -0.5 * Math.PI
    val angle = 2.0 * Math.PI / 5.0f
    val radiusList = TestScore(12, 13, 13, 13, 13).toFloatList()
    LaunchedEffect(true) {
        delay(10)
        textVisible = true
    }
    with(LocalDensity.current) {
        val radiusPxList = radiusList.onEach { radius -> radius.dp.toPx() }
        AnimatedVisibility(
            visible = textVisible,
            enter = scaleIn(
                tween(durationMillis = 1600)
            ) +
                expandVertically(expandFrom = Alignment.CenterVertically)
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val center = Offset(
                    this@BoxWithConstraints.maxWidth.toPx() / 2f,
                    this@BoxWithConstraints.maxHeight.toPx() / 2f
                )
                for (i in 0 until 5) {
                    Text(
                        text = if (textVisible) textList[i] else "",
                        modifier = Modifier
                            .offset(
                                (center.x + (radiusPxList[i] * cos(currentAngle + angle * i)).toFloat() + xSite[i]).toDp(),
                                (center.y + (radiusPxList[i] * sin(currentAngle + angle * i)).toFloat() + ySite[i]).toDp()
                            ),
                        style = HousTheme.typography.description,
                        color = colorResource(id = R.color.hous_g_5),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
