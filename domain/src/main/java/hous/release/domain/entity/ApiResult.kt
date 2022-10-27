package hous.release.domain.entity

sealed class ApiResult<out T> {
    object Empty : ApiResult<Nothing>()
    data class Error(val msg: String?) : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
}
