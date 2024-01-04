package com.github.goutarouh.notionboost.data.datastore

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

    override suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        monthlyWidgetConfiguration.putAll(config)
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