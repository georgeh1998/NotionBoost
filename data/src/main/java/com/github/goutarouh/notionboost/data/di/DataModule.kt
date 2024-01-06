package com.github.goutarouh.notionboost.data.di

import android.content.Context
import com.github.goutarouh.notionboost.data.api.ApiHeaderInterceptor
import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.api.queryDatabase.QueryDatabaseApiResponseModel
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.data.datastore.DataStoreApiImpl
import com.github.goutarouh.notionboost.data.datastore.settingDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().apply {
            registerTypeAdapter(
                QueryDatabaseApiResponseModel.Result.Properties::class.java,
                JsonDeserializer { json, _, _ ->
                    QueryDatabaseApiResponseModel.Result.Properties.createFromJson(json)
                }
            )
        }.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                ApiHeaderInterceptor(
                notionVersion = "2022-06-28"
            )
            )
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.notion.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideNotionRemoteApi(
        retrofit: Retrofit
    ): NotionRemoteApi {
        return retrofit.create(NotionRemoteApi::class.java)
    }


    @Provides
    @Singleton
    fun provideDataStoreApi(
        @ApplicationContext context: Context,
        gson: Gson,
    ) : DataStoreApi {
        return DataStoreApiImpl(
            dataStore = context.settingDataStore,
            gson = gson,
        )
    }
}