package com.github.goutarouh.notionboost.data.glance.monthly

data class MonthlyWidgetLocalModel(
    val monthName: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val lastUpdatedTime: String,
    val databaseId: String,
    val url: String,
    val mapProgress: Map<String, Float> = mapOf(),
)