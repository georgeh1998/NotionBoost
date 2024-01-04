package com.github.goutarouh.notionboost.repository

import com.github.goutarouh.notionboost.data.NotionRemoteApi
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.widget.GlanceApi
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
        dataStoreApi: DataStoreApi,
    ): NotionDatabaseRepository {
        return NotionDatabaseRepositoryImpl(
            notionRemoteApi = notionRemoteApi,
            dataStoreApi = dataStoreApi,
        )
    }

    @Provides
    @Singleton
    fun provideGlanceRepository(
        glanceApi: GlanceApi,
    ): GlanceRepository {
        return GlanceRepository(
            glanceApi = glanceApi
        )
    }

}