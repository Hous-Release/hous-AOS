package hous.release.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: Token,
    val userId: String
)

data class Token(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
