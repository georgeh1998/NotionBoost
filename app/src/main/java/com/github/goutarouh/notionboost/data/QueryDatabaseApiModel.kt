package com.github.goutarouh.notionboost.data

import com.google.gson.annotations.SerializedName

data class QueryDatabaseApiModel(
    val results: List<Result>,
) {

    data class Result(
        val properties: Properties,
    ) {

        data class Properties(
            @SerializedName("Created time")
            val createdTime: CreatedTime,
            @SerializedName("English Learning")
            val englishLearning: EnglishLearning,
            @SerializedName("Muscle training")
            val muscleTraining: MuscleTraining,
            @SerializedName("Reading")
            val reading: Reading,
            @SerializedName("Sleep until 24")
            val sleepUntil24: SleepUntil24,
        ) {
            data class CreatedTime(
                @SerializedName("created_time")
                val createdTime: String,
                val id: String,
                val type: String
            )

            data class EnglishLearning(
                val checkbox: Boolean,
                val id: String,
                val type: String
            )

            data class MuscleTraining(
                val checkbox: Boolean,
                val id: String,
                val type: String
            )

            data class Name(
                val id: String,
                val title: List<Any>,
                val type: String
            )

            data class Reading(
                val checkbox: Boolean,
                val id: String,
                val type: String
            )

            data class SleepUntil24(
                val checkbox: Boolean,
                val id: String,
                val type: String
            )
        }
    }
}