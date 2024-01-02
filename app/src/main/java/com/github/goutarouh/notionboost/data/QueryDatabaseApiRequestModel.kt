package com.github.goutarouh.notionboost.data

import com.google.gson.annotations.SerializedName

data class QueryDatabaseApiAndRequestModel(
    val filter: Filter
) {
    data class Filter(
        val and: List<And>
    )

    data class And(
        val date: Date,
        val `property`: String
    )

    sealed class Date {
        data class OnOrAfter(
            @SerializedName("on_or_after")
            val onOrAfter: String
        ) : Date()

        data class OnOrBefore(
            @SerializedName("on_or_before")
            val onOrBefore: String
        ) : Date()
    }
}