package hous.release.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: Token,
    val userId: String
)

