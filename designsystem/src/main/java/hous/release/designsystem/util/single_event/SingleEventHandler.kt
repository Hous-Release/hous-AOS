package hous.release.designsystem.util.single_event

class SingleEventHandler(
    private val singleEventHandleStrategy: SingleEventHandleStrategy = ThrottledDurationStrategy()
) {
    fun handle(event: () -> Unit) {
        singleEventHandleStrategy.handle(event)
    }
}
