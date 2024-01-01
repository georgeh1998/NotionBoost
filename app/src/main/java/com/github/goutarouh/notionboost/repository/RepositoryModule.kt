package com.github.goutarouh.notionboost.repository

import android.content.Context
import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideNotionDatabaseRepository(
        @ApplicationContext applicationContext: Context,
        gson: Gson,
        notionRemoteApi: NotionRemoteApi,
    ): NotionDatabaseRepository {
        return NotionDatabaseRepositoryImpl(
            applicationContext = applicationContext,
            gson = gson,
            notionRemoteApi = notionRemoteApi,
        )
    }

}