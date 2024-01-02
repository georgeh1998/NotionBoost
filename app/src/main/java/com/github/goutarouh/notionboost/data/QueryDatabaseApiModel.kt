package com.github.goutarouh.notionboost.data

import com.google.gson.annotations.SerializedName

data class QueryDatabaseApiModel(
    val results: List<Result> = listOf(),
) {

    data class Result(
        val properties: Properties = Properties(),
    ) {

        data class Properties(
            @SerializedName("Created time")
            val createdTime: CreatedTime = CreatedTime(),
            @SerializedName("English Learning")
            val englishLearning: EnglishLearning = EnglishLearning(),
            @SerializedName("Muscle training")
            val muscleTraining: MuscleTraining = MuscleTraining(),
            @SerializedName("Reading")
            val reading: Reading = Reading(),
            @SerializedName("Sleep until 24")
            val sleepUntil24: SleepUntil24 = SleepUntil24(),
        ) {
            data class CreatedTime(
                @SerializedName("created_time")
                val createdTime: String = "",
            )

            data class EnglishLearning(
                val checkbox: Boolean = false,
            )

            data class MuscleTraining(
                val checkbox: Boolean = false,
            )

            data class Reading(
                val checkbox: Boolean = false,
            )

            data class SleepUntil24(
                val checkbox: Boolean = false,
            )
        }
    }
}