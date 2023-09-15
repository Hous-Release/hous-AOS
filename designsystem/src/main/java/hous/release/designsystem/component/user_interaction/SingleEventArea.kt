package hous.release.designsystem.component.user_interaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import hous.release.designsystem.util.SingleEventHandler

/**
 * 여러 번 클릭 이벤트를 막아주는 Wrapper Composable
 * */
@Composable
fun <T> SingleEventArea(
    content: @Composable (SingleEventHandler) -> T
) {
    val singleEventHandler = remember { SingleEventHandler.instance() }

    content(singleEventHandler)
}
