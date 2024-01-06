package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.api.retrieveDatabase.RetrieveDatabaseApiResponseModel

data class RetrieveDatabaseModel(
    val title: String,
    val url: String,
)

fun RetrieveDatabaseApiResponseModel.toModel(): RetrieveDatabaseModel {
    return RetrieveDatabaseModel(
        title = this.title.firstOrNull()?.plainText ?: "",
        url = this.url,
    )
}
