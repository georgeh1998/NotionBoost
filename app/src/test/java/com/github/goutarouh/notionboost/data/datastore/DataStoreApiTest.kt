package com.github.goutarouh.notionboost.data.datastore

class FakeDataStoreApi: DataStoreApi {

    private var setNotionApiKey: String = ""

    override suspend fun setNotionApiKey(notionApiKey: String) {
        setNotionApiKey = notionApiKey
    }

    override suspend fun getNotionApiKey(): String {
        return setNotionApiKey
    }

}