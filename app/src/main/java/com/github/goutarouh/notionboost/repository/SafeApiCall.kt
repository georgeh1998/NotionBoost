package com.github.goutarouh.notionboost.repository

import com.google.gson.JsonSyntaxException
import java.net.UnknownHostException
import retrofit2.HttpException


sealed class ApiException : Exception() {
    class InternetException : ApiException()

    class ResponseUnexpectedException(override val cause: Throwable?) : ApiException()

    class UnExpectedException(override val cause: Throwable?) : ApiException()
}

suspend fun <T> safeApiCall(
    call: suspend () -> T
) : T {
    try {
        return call()
    } catch (e: HttpException) {
        // TODO Handle based on status code
        throw ApiException.UnExpectedException(e.cause)
    } catch (e: JsonSyntaxException) {
        throw ApiException.ResponseUnexpectedException(e.cause)
    } catch (e: UnknownHostException) {
        throw ApiException.InternetException()
    } catch (e: Exception) {
        throw ApiException.UnExpectedException(e.cause)
    }
}




