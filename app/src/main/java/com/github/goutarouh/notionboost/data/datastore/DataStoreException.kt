package com.github.goutarouh.notionboost.data.datastore

sealed class DataStoreException : Exception() {
    class NotSetException(
        override val message: String?
    ) : DataStoreException()

    class UnExpectedException(override val cause: Throwable?) : DataStoreException()

    class TimeOutException(override val message: String?) : DataStoreException()

}