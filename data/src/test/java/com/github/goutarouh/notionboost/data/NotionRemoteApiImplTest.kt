package com.github.goutarouh.notionboost.data

import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.retrieveDatabase.RetrieveDatabaseApiResponseModel

fun createNotionRemoteApi(
    retrieveDatabaseApiResponseModel: RetrieveDatabaseApiResponseModel = RetrieveDatabaseApiResponseModel(),
    queryDatabaseApiResponseModel: QueryDatabaseApiResponseModel = QueryDatabaseApiResponseModel(),
) : NotionRemoteApi {
    return object : NotionRemoteApi {
        override suspend fun retrieveDatabase(
            authorization: String,
            databaseId: String
        ): RetrieveDatabaseApiResponseModel {
            return retrieveDatabaseApiResponseModel
        }

        override suspend fun queryDatabase(
            authorization: String,
            databaseId: String,
            queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
        ): QueryDatabaseApiResponseModel {
            return queryDatabaseApiResponseModel
        }
    }
}


