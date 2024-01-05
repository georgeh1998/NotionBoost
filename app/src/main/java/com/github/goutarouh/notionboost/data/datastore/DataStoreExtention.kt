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


val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

suspend fun <T> Flow<Preferences>.getValue(
    key: Preferences.Key<T>,
    timeoutMill: Long = 100L
) : T = withContext(Dispatchers.IO) {

    val flow = this@getValue

    try {
        withTimeout(timeoutMill) {
            val pref = flow.first()
            pref[key]
                ?: throw DataStoreException.NotSetException(
                    message = "DataStore Preferences Not Set. key: ${key.name}"
                )
        }
    } catch (e: IOException) {
        throw DataStoreException.UnExpectedException(e.cause)
    } catch (e: TimeoutCancellationException) {
        throw DataStoreException.TimeOutException(
            "DataStore Preferences Timeout (${timeoutMill}ms). key: ${key.name}"
        )
    }
}

object DataStoreKey {
    val NOTION_API_KEY = stringPreferencesKey("NOTION_API_KEY")

    val APP_WIDGET_ID_TO_DATABASE_ID = stringPreferencesKey("APP_WIDGET_ID_TO_DATABASE_ID")

    val monthlyWidgetModel = stringPreferencesKey("monthlyWidgetModel")
}