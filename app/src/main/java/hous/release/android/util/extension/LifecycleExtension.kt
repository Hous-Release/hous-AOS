package hous.release.android.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
