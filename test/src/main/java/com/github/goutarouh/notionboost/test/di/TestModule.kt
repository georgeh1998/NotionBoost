package com.github.goutarouh.notionboost.test.di

import com.github.goutarouh.notionboost.data.api.NotionRemoteApi
import com.github.goutarouh.notionboost.data.datastore.DataStoreApi
import com.github.goutarouh.notionboost.data.di.DataModule
import com.github.goutarouh.notionboost.data.glance.GlanceApi
import com.github.goutarouh.notionboost.test.data.FakeDataStoreApi
import com.github.goutarouh.notionboost.test.data.FakeGlanceApi
import com.github.goutarouh.notionboost.test.data.FakeNotionRemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestModule {

    @Provides
    @Singleton
    fun provideNotionRemoteApi(
    ): NotionRemoteApi {
        return FakeNotionRemoteApi()
    }

    @Provides
    @Singleton
    fun provideDataStoreApi() : DataStoreApi {
        return FakeDataStoreApi()
    }

    @Provides
    @Singleton
    fun provideGlanceApi() : GlanceApi {
        return FakeGlanceApi()
    }

}