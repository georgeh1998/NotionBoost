package com.github.goutarouh.notionboost.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiHeaderInterceptor(
    private val notionVersion: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            addHeader("Notion-Version", notionVersion)
            addHeader("Content-Type", "application/json")
        }.build()
        return chain.proceed(newRequest)
    }
}
