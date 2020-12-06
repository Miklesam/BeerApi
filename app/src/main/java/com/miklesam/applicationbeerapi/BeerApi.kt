package com.miklesam.applicationbeerapi

import androidx.lifecycle.LiveData
import com.miklesam.applicationbeerapi.di.Beer
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BeerApi {
    @GET("beers/random")
    suspend fun getRandomBeer(): Response<List<Beer>>

    companion object {
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}