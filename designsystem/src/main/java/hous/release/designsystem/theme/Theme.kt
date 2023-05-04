package hous.release.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalHousTypography = staticCompositionLocalOf<HousTypography> {
    error("No HousTypography provided")
}

object HousTheme {
    val typography: HousTypography @Composable get() = LocalHousTypography.current
}

@Composable
fun ProvideHousTypography(
    typography: HousTypography,
    content: @Composable () -> Unit
) {
    val provideTypography = remember { typography.copy() }
    CompositionLocalProvider(
        LocalHousTypography provides provideTypography,
        content = content
    )
}

@Composable
fun HousTheme(
    content: @Composable () -> Unit
) {
    val typography = HousTypography()
    ProvideHousTypography(typography = typography) {
        MaterialTheme(content = content)
    }
}
