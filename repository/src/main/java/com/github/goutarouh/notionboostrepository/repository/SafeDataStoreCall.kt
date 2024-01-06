package com.github.goutarouh.notionboostrepository.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

sealed class DataStoreException : Exception() {
    class NotSetException(
        override val cause: Throwable?
    ) : DataStoreException()

    class UnExpectedException(override val cause: Throwable?) : DataStoreException()

    class TimeOutException(
        override val cause: Throwable?
    ) : DataStoreException()

}

suspend fun <T> getDataStoreCall(
    timeoutMill: Long = 100L,
    call: suspend () -> T?
) : T = withContext(Dispatchers.IO) {
    try {
        withTimeout(timeoutMill) {
            val result = call()
            if (result == null) {
                throw NoSuchElementException()
            } else {
                return@withTimeout result
            }
        }
    } catch (e: NoSuchElementException) {
        throw DataStoreException.NotSetException(e.cause)
    } catch (e: IOException) {
        throw DataStoreException.UnExpectedException(e.cause)
    } catch (e: TimeoutCancellationException) {
        throw DataStoreException.TimeOutException(e.cause)
    }
}
