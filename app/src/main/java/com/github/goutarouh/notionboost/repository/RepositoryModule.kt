package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNotionDatabaseRepository(
        notionRemoteApi: NotionRemoteApi,
    ): NotionDatabaseRepository {
        return NotionDatabaseRepositoryImpl(notionRemoteApi)
    }

}