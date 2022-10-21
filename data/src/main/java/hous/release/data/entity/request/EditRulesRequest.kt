package hous.release.data.entity.request

import com.google.gson.annotations.SerializedName

data class EditRulesRequest(
    @SerializedName("rulesIdList")
    val rulesIdList: List<Int> = emptyList()
)
