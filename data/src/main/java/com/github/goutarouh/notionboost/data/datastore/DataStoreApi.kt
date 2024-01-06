package com.github.goutarouh.notionboost.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DataStoreApi {

    suspend fun setNotionApiKey(notionApiKey: String)

    suspend fun getNotionApiKey() : String?

    suspend fun saveMonthlyWidgetConfiguration(config: Map<Int, String>)

    suspend fun removeMonthlyWidgetConfiguration(appWidgetIds: List<Int>)

    suspend fun getMonthlyWidgetConfiguration() : Map<Int, String>

    fun monthlyWidgetConfigurationFlow() : Flow<Map<Int, String>>

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

    override suspend fun getNotionApiKey(): String? {
        return prefFlow.getValueOrNull(DataStoreKey.NOTION_API_KEY)
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
        return try {
            val mapJson = prefFlow.getValueOrNull(DataStoreKey.APP_WIDGET_ID_TO_DATABASE_ID)
            val mapType = object : TypeToken<Map<Int, String>>() {}.type
            gson.fromJson(mapJson, mapType)
        } catch (e: NoSuchElementException) {
            mapOf()
        }
    }

    override suspend fun removeMonthlyWidgetConfiguration(appWidgetIds: List<Int>) {
        val map = getMonthlyWidgetConfiguration().toMutableMap().apply {
            appWidgetIds.forEach {
                remove(it)
            }
        }
        val mapJson = gson.toJson(map)
        dataStore.edit { settings ->
            settings[DataStoreKey.APP_WIDGET_ID_TO_DATABASE_ID] = mapJson
        }
    }

    override fun monthlyWidgetConfigurationFlow(): Flow<Map<Int, String>> {
        return dataStore.data.map {
            val mapJson = it[DataStoreKey.APP_WIDGET_ID_TO_DATABASE_ID]
            val mapType = object : TypeToken<Map<Int, String>>() {}.type
            if (mapJson == null) {
                mapOf()
            } else {
                gson.fromJson(mapJson, mapType)
            }
        }
    }

}