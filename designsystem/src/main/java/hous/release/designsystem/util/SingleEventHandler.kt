package hous.release.designsystem.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

/**
 * 여러 번 클릭 이벤트를 막아주는 EventHandler
 * */
@OptIn(ExperimentalTime::class)
fun interface SingleEventHandler {

    fun handle(event: () -> Unit)

    private class SingleEventHandlerImpl : SingleEventHandler {
        private val currentTime: TimeMark get() = TimeSource.Monotonic.markNow()
        private val throttleDuration: Duration = 300.milliseconds
        private lateinit var lastEventTime: TimeMark

        override fun handle(event: () -> Unit) {
            if (::lastEventTime.isInitialized.not() || (lastEventTime + throttleDuration).hasPassedNow()) {
                event()
            }
            lastEventTime = currentTime
        }
    }

    companion object {
        fun instance(): SingleEventHandler = SingleEventHandlerImpl()
    }
}