package com.github.goutarouh.notionboost.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeout
import java.io.IOException


val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

suspend fun <T> Flow<Preferences>.getDataStoreValue(
    key: Preferences.Key<T>,
    timeoutMill: Long = 1000L
) : T {

    val flow = this

    try {
        return withTimeout(timeoutMill) {
            val preferencesFlow = flow.map { preferences ->
                preferences[key]
            }
            preferencesFlow.first() ?: throw NoSuchElementException()
        }
    } catch (e: NoSuchElementException) {
        throw DataStoreException.NotSetException(key.name)
    } catch (e: IOException) {
        throw DataStoreException.UnExpectedException(e.cause)
    } catch (e: TimeoutCancellationException) {
        throw DataStoreException.TimeOutException()
    }
}

object DataStoreKey {
    val NOTION_API_KEY = stringPreferencesKey("NOTION_API_KEY")

    val DATABASE_ID = stringPreferencesKey("DATABASE_ID")
}