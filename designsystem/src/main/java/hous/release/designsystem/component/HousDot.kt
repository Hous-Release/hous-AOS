package hous.release.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousBlueL1
import hous.release.designsystem.theme.HousG3
import hous.release.designsystem.theme.HousTheme

enum class HousDotType {
    NEW,
    NORMAL,
    REPRESENTATIVE;

    companion object {
        fun from(isNew: Boolean = false, isRepresent: Boolean = false): HousDotType {
            return when {
                isNew -> NEW
                isRepresent -> REPRESENTATIVE
                else -> NORMAL
            }
        }
    }
}

@Composable
fun HousDot(
    housDotType: HousDotType = HousDotType.NORMAL
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(
                color = when (housDotType) {
                    HousDotType.NEW -> HousBlueL1
                    HousDotType.NORMAL -> HousG3
                    HousDotType.REPRESENTATIVE -> HousBlue
                }
            )
    )
}

@Preview(name = "newType - hous dash", showBackground = true)
@Composable
private fun HousDashPreview() {
    HousTheme {
        HousDot(HousDotType.from(isNew = true))
    }
}

@Preview(name = "hous dash", showBackground = true)
@Composable
private fun HousDashPreview2() {
    HousTheme {
        HousDot()
    }
}

@Preview(name = "representative - hous dash", showBackground = true)
@Composable
private fun HousDashPreview3() {
    HousTheme {
        HousDot(HousDotType.from(isRepresent = true))
    }
}
