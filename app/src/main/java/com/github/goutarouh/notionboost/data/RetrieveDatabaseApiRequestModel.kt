package com.github.goutarouh.notionboost.data

import com.google.gson.annotations.SerializedName

data class RetrieveDatabaseApiRequestModel(
    val title: List<Title> = listOf(),
    val url: String = ""
) {
    data class Title(
        @SerializedName("plain_text")
        val plainText: String = "",
    )
}