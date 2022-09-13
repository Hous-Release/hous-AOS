package hous.release.data.entity.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val `data`: T,
    val message: String,
    val status: Int,
    val success: Boolean
)
