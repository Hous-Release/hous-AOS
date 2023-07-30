package hous.release.android.util.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    source1: Flow<T1>,
    source2: Flow<T2>,
    source3: Flow<T3>,
    source4: Flow<T4>,
    source5: Flow<T5>,
    source6: Flow<T6>,
    source7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(
    source1, source2, source3, source4, source5, source6, source7
) { values ->
    transform(
        values[0] as T1,
        values[1] as T2,
        values[2] as T3,
        values[3] as T4,
        values[4] as T5,
        values[5] as T6,
        values[6] as T7
    )
}
