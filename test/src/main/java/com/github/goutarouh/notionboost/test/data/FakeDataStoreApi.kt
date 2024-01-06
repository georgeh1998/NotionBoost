package com.github.goutarouh.notionboost.test.data

import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreApi : DataStoreApi {

    private var notionApiKey: String? = null
    private var configurations: MutableMap<Int, String> = mutableMapOf()

    override suspend fun setNotionApiKey(notionApiKey: String) {
        this.notionApiKey = notionApiKey
    }

    override suspend fun getNotionApiKey(): String? = notionApiKey

    override suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        configurations.putAll(config)
    }

    override suspend fun removeMonthlyWidgetConfiguration(appWidgetIds: List<Int>) {
        appWidgetIds.forEach { configurations.remove(it) }
    }

    override suspend fun getMonthlyWidgetConfiguration(): Map<Int, String> = configurations

    override fun monthlyWidgetConfigurationFlow(): Flow<Map<Int, String>> {
        return flow { emit(configurations) }
    }

}