package com.github.goutarouh.notionboost.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface DataStoreApi {

    suspend fun setNotionApiKey(notionApiKey: String)

    suspend fun getNotionApiKey() : String

    suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>)

    suspend fun getMonthlyWidgetConfiguration() : Map<Int, String>

}


class DataStoreApiImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
) : DataStoreApi {

    private val prefFlow = dataStore.data

    override suspend fun setNotionApiKey(notionApiKey: String) {
        dataStore.edit { settings ->
            settings[DataStoreKey.NOTION_API_KEY] = notionApiKey
        }
    }

    override suspend fun getNotionApiKey(): String {
        return prefFlow.getDataStoreValue(DataStoreKey.NOTION_API_KEY)
    }

    override suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>) {
        val map = getMonthlyWidgetConfiguration().toMutableMap().apply {
            putAll(config)
        }
        val mapJson = gson.toJson(map)
        dataStore.edit { settings ->
            settings[DataStoreKey.APP_WIDGET_ID_TO_DATABASE_ID] = mapJson
        }
    }

    override suspend fun getMonthlyWidgetConfiguration(): Map<Int, String> {
        val mapJson = prefFlow.getDataStoreValue(DataStoreKey.APP_WIDGET_ID_TO_DATABASE_ID)
        val mapType = object : TypeToken<Map<Int, String>>() {}.type
        return gson.fromJson(mapJson, mapType)
    }

}