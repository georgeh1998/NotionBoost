package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi

interface NotionDatabaseRepository {

    suspend fun queryDatabase(databaseId: String) : QueryDatabaseModel

}


class NotionDatabaseRepositoryImpl(
    private val notionRemoteApi: NotionRemoteApi
) : NotionDatabaseRepository {

    override suspend fun queryDatabase(databaseId: String) : QueryDatabaseModel {
        return notionRemoteApi.queryDatabase(databaseId).toModel()
    }
}



