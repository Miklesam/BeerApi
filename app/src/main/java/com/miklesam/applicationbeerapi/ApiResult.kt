package com.miklesam.applicationbeerapi

sealed class ApiResult <out T>(val data : T?, val exception : Throwable?){
    class Failure<T>(exception: Throwable) : ApiResult<T>(null, exception)
    class Success<T>(data: T) : ApiResult<T>(data, null)

    companion object {
        fun <T> success(value: T): ApiResult<T> = Success(value)
        fun <T> failure(exception: Throwable): Failure<T> = Failure(exception)
    }
}