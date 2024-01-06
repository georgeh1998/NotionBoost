package com.github.goutarouh.notionboost.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException


internal val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

suspend fun <T> Flow<Preferences>.getValueOrNull(
    key: Preferences.Key<T>,
) : T? {
    return this@getValueOrNull.first()[key]
}


object DataStoreKey {
    val NOTION_API_KEY = stringPreferencesKey("NOTION_API_KEY")

    val APP_WIDGET_ID_TO_DATABASE_ID = stringPreferencesKey("APP_WIDGET_ID_TO_DATABASE_ID")

    val monthlyWidgetKey = stringPreferencesKey("monthlyWidget")
}