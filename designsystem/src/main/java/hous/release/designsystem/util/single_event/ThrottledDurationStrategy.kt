package hous.release.designsystem.util.single_event

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class)
internal class ThrottledDurationStrategy : SingleEventHandleStrategy {
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
