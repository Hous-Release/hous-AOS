package hous.release.android.util.event

fun interface Reducer<S, E> {
    fun dispatch(state: S, event: E): S
}
