package com.github.goutarouh.notionboost.data

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface NotionRemoteApi {

    @POST("databases/{databaseId}/query")
    suspend fun queryDatabase(
        @Header("Authorization") authorization: String,
        @Path("databaseId") databaseId: String,
        @Body queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
    ): QueryDatabaseApiModel

}