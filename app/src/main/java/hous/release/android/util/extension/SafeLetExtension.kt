package hous.release.android.util.extension

/**
 * 변수 2가지 이상 null 체크할 때 사용
 *
 * @param T1
 * @param T2
 * @param R
 * @param p1
 * @param p2
 * @param block
 * @return
 */
inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
