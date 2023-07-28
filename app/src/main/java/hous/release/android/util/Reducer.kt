package hous.release.android.util

fun interface Reducer<S, E> {
    fun dispatch(state: S, event: E): S
}
