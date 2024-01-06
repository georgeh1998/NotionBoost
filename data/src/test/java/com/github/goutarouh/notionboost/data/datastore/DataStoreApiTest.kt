package com.github.goutarouh.notionboost.data.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreApi: DataStoreApi {

    private var setNotionApiKey: String = ""

    override suspend fun setNotionApiKey(notionApiKey: String) {
        setNotionApiKey = notionApiKey
    }

    override suspend fun getNotionApiKey(): String {
        return setNotionApiKey
    }

    private val monthlyWidgetConfiguration = mutableMapOf<Int, String>()

    override suspend fun getMonthlyWidgetConfiguration(): Map<Int, String> {
        return monthlyWidgetConfiguration
    }

    override fun monthlyWidgetConfigurationFlow(): Flow<Map<Int, String>> {
        return flow { emit(monthlyWidgetConfiguration) }
    }

    override suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        monthlyWidgetConfiguration.putAll(config)
    }

    override suspend fun removeMonthlyWidgetConfiguration(appWidgetIds: List<Int>) {
        appWidgetIds.forEach {
            monthlyWidgetConfiguration.remove(it)
        }
    }

}

suspend fun createFakeDataStoreApi(
    config: Map<Int, String> = mapOf(),
    notionApiKey: String = "",
): DataStoreApi {
    return FakeDataStoreApi().apply {
        setNotionApiKey(notionApiKey)
        saveMonthlyWidgetConfiguration(config)
    }
}