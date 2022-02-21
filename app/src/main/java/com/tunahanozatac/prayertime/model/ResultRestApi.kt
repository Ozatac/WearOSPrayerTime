package com.tunahanozatac.prayertime.model

import retrofit2.HttpException

sealed class ResultRestApi<out R> {
    data class Success<out T>(val data: T) : ResultRestApi<T>()
    data class HttpError(val exception: HttpException) : ResultRestApi<Nothing>()
    data class Error(val exception: Exception) : ResultRestApi<Nothing>()
}
