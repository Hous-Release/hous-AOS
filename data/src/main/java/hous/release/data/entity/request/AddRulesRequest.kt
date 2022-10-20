package hous.release.data.entity.request

import com.google.gson.annotations.SerializedName

data class AddRulesRequest(
    @SerializedName("ruleNames")
    val addedRules: List<String>
)
