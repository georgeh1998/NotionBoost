package com.github.goutarouh.notionboost.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface NotionRemoteApi {

    @POST("databases/{databaseId}/query")
    suspend fun queryDatabase(
        @Path("databaseId") databaseId: String,
        @Body queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
    ): QueryDatabaseApiModel

}