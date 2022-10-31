package hous.release.android.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Lifecycle에 맞게 알아서 collect/cancel을 반복해주게 해주는 확장 함수
 *
 * @param block
 */
inline fun LifecycleOwner.repeatOnStarted(crossinline block: suspend () -> Unit) {
    when (this) {
        is AppCompatActivity -> {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    block()
                }
            }
        }
        is Fragment -> {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    block()
                }
            }
        }
    }
}

/**
 * composable이 보이지 않을 때도 계속 값을 수집한다는 문제가 있다.
 * 우리는 Flow에 생명주기를 입혀 Lifecycledl STARTED일 때만 수집 가능하게 만들어주는 확장함수
 *
 * @param T
 * @param initial
 * @param minActiveState
 * @return State<T>
 */
@Composable
fun <T> Flow<T>.collectAsStateWithLifecycleRemember(
    initial: T,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val flowLifecycleAware = remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
    }
    return flowLifecycleAware.collectAsState(initial)
}
