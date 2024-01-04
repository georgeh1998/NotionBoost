package com.github.goutarouh.notionboost.data.datastore

sealed class DataStoreException : Exception() {

    class NotSetException(val keyName: String) : DataStoreException()

    class UnExpectedException(override val cause: Throwable?) : DataStoreException()

    class TimeOutException : DataStoreException()

}