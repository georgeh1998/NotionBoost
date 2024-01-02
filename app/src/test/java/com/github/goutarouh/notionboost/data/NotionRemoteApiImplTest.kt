package com.github.goutarouh.notionboost.data

fun createNotionRemoteApi(
    queryDatabaseApiModel: QueryDatabaseApiModel
) : NotionRemoteApi {
    return object : NotionRemoteApi {
        override suspend fun queryDatabase(
            databaseId: String,
            queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
        ): QueryDatabaseApiModel {
            return queryDatabaseApiModel
        }
    }
}


