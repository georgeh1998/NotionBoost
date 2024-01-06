package com.github.goutarouh.notionboost.data.api.retrieveDatabase

import com.google.gson.annotations.SerializedName

data class RetrieveDatabaseApiResponseModel(
    val title: List<Title> = listOf(),
    val url: String = ""
) {
    data class Title(
        @SerializedName("plain_text")
        val plainText: String = "",
    )
}