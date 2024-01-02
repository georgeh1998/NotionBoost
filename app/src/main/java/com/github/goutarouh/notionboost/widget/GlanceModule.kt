package com.github.goutarouh.notionboost.widget

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlanceModule {

    @Provides
    @Singleton
    fun provideGlanceApi(
        @ApplicationContext applicationContext: Context,
        gson: Gson,
    ): GlanceApi {
        return GlanceApiImpl(
            applicationContext = applicationContext,
            gson = gson,
        )
    }

}