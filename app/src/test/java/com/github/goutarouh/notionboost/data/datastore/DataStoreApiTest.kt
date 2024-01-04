package com.github.goutarouh.notionboost.data.datastore

class FakeDataStoreApi: DataStoreApi {

    private var setNotionApiKey: String = ""
    private var setDatabaseId: String = ""

    override suspend fun setNotionApiKey(notionApiKey: String) {
        setNotionApiKey = notionApiKey
    }

    override suspend fun getNotionApiKey(): String {
        return setNotionApiKey
    }

    override suspend fun setDatabaseId(databaseId: String) {
        setDatabaseId = databaseId
    }

    override suspend fun getDatabaseId(): String {
        return setDatabaseId
    }

}