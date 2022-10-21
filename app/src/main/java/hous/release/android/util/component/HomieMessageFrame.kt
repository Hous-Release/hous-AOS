package hous.release.android.util.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp

const val LEFT = 0
const val RIGHT = 1

fun DrawScope.drawMessageShape(
    side: Dp,
    firstX: Dp,
    secondX: Dp,
    cornerRadius: Dp,
    messageColor: Color,
    direction: Int
) {
    if (direction == LEFT) {
        drawLeftMessageShape(
            side,
            firstX,
            secondX,
            cornerRadius,
            messageColor
        )
    } else if (direction == RIGHT) {
        drawRightMessageShape(
            side,
            firstX,
            secondX,
            cornerRadius,
            messageColor
        )
    }
}

private fun DrawScope.drawRightMessageShape(
    side: Dp,
    firstX: Dp,
    secondX: Dp,
    cornerRadius: Dp,
    messageColor: Color
) {
    val triangleShape = Path().apply {
        drawRightTriangle(
            side = side.toPx(),
            size = size
        )
    }
    val messageShape = Path().apply {
        drawRightMessageShape(
            firstX = firstX.toPx(),
            secondX = secondX.toPx(),
            size = size
        )
    }
    drawIntoCanvas { canvas ->
        canvas.drawOutline(
            outline = Outline.Generic(triangleShape),
            paint = Paint().apply {
                color = messageColor
            }
        )
        canvas.drawOutline(
            outline = Outline.Generic(messageShape),
            paint = Paint().apply {
                color = messageColor
                pathEffect = PathEffect.cornerPathEffect(cornerRadius.toPx())
            }
        )
    }
}

private fun DrawScope.drawLeftMessageShape(
    side: Dp,
    firstX: Dp,
    secondX: Dp,
    cornerRadius: Dp,
    messageColor: Color
) {
    val triangleShape = Path().apply {
        drawLeftTriangle(
            side = side.toPx(),
            size = size
        )
    }
    val messageShape = Path().apply {
        drawLeftMessageShape(
            firstX = firstX.toPx(),
            secondX = secondX.toPx(),
            size = size
        )
    }
    drawIntoCanvas { canvas ->
        canvas.drawOutline(
            outline = Outline.Generic(triangleShape),
            paint = Paint().apply {
                color = messageColor
            }
        )
        canvas.drawOutline(
            outline = Outline.Generic(messageShape),
            paint = Paint().apply {
                color = messageColor
                pathEffect = PathEffect.cornerPathEffect(cornerRadius.toPx())
            }
        )
    }
}

private fun Path.drawRightTriangle(side: Float, size: Size) {
    reset()
    moveTo(0f, size.height / 2)
    lineTo(side, size.height / 4)
    lineTo(side, 3 * (size.height / 4))
    lineTo(0f, size.height / 2)
    close()
}

private fun Path.drawRightMessageShape(firstX: Float, secondX: Float, size: Size) {
    reset()
    moveTo(firstX, size.height / 4)
    lineTo(secondX, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(secondX, size.height)
    lineTo(firstX, 3 * (size.height / 4))
    lineTo(firstX, size.height / 4)
    close()
}

private fun Path.drawLeftTriangle(side: Float, size: Size) {
    reset()
    moveTo(size.width - side, size.height / 4)
    lineTo(size.width, size.height / 2)
    lineTo(size.width - side, 3 * (size.height / 4))
    lineTo(size.width - side, size.height / 4)
    close()
}

private fun Path.drawLeftMessageShape(firstX: Float, secondX: Float, size: Size) {
    reset()
    moveTo(0f, 0f)
    lineTo(size.width - secondX, 0f)
    lineTo(size.width - firstX, size.height / 4)
    lineTo(size.width - firstX, 3 * (size.height / 4))
    lineTo(size.width - secondX, size.height)
    lineTo(0f, size.height)
    lineTo(0f, 0f)
    close()
}
