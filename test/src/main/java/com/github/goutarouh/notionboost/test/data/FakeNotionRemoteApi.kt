package com.github.goutarouh.notionboost.test.data

import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.retrieveDatabase.RetrieveDatabaseApiResponseModel

class FakeNotionRemoteApi : NotionRemoteApi {

    private var retrieveDatabaseModel: RetrieveDatabaseApiResponseModel? = null
    private var queryDatabaseModel: QueryDatabaseApiResponseModel? = null


    override suspend fun setRetrieveDatabase(model: RetrieveDatabaseApiResponseModel) {
        retrieveDatabaseModel = model
    }

    override suspend fun retrieveDatabase(
        authorization: String,
        databaseId: String
    ): RetrieveDatabaseApiResponseModel {
        return retrieveDatabaseModel!!
    }

    override suspend fun setQueryDatabase(model: QueryDatabaseApiResponseModel) {
        queryDatabaseModel = model
    }

    override suspend fun queryDatabase(
        authorization: String,
        databaseId: String,
        queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
    ): QueryDatabaseApiResponseModel {
        return queryDatabaseModel!!
    }
}