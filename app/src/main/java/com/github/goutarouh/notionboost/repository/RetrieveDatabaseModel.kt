package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.RetrieveDatabaseApiRequestModel

data class RetrieveDatabaseModel(
    val title: String,
    val url: String,
)

fun RetrieveDatabaseApiRequestModel.toModel(): RetrieveDatabaseModel {
    return RetrieveDatabaseModel(
        title = this.title.firstOrNull()?.plainText ?: "",
        url = this.url,
    )
}
