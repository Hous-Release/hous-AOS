package hous.release.designsystem.util.single_event

fun interface SingleEventHandleStrategy {
    fun handle(event: () -> Unit)
}
