package com.github.goutarouh.notionboost.data

import android.util.Log
import com.github.goutarouh.notionboost.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(ApiHeaderInterceptor(
                authorization = BuildConfig.API_KEY,
                notionVersion = "2022-06-28"
            ))
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.notion.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotionRemoteApi(
        retrofit: Retrofit
    ): NotionRemoteApi {
        return retrofit.create(NotionRemoteApi::class.java)
    }

}