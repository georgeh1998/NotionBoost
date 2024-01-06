package com.github.goutarouh.notionboost.data.api

import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiAndRequestModel
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.api.retrieveDatabase.RetrieveDatabaseApiResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface NotionRemoteApi {

    @GET("databases/{databaseId}")
    suspend fun retrieveDatabase(
        @Header("Authorization") authorization: String,
        @Path("databaseId") databaseId: String,
    ) : RetrieveDatabaseApiResponseModel

    @POST("databases/{databaseId}/query")
    suspend fun queryDatabase(
        @Header("Authorization") authorization: String,
        @Path("databaseId") databaseId: String,
        @Body queryDatabaseApiAndRequestModel: QueryDatabaseApiAndRequestModel
    ): QueryDatabaseApiResponseModel

}