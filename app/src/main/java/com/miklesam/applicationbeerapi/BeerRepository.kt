package com.miklesam.applicationbeerapi

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepository @Inject constructor(private val beerApi: BeerApi) {

    suspend fun getRandomBeer(): ApiResult<List<Beer>?> {
        try {
            val response = beerApi.getRandomBeer()
            if (response.isSuccessful) {
                val users = response.body()
                Log.w("Beer", users.toString())
                return ApiResult.success(users)
            } else {
                Log.d("Beer ", response.errorBody().toString())
                return ApiResult.failure(RuntimeException("Network unavailable"))
            }
        } catch (e: Exception) {
            return ApiResult.failure(RuntimeException("Network unavailable"))
        }

    }


    fun getSearchBeerResult(food: String) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BeerPagingSource(beerApi, food) }
        ).liveData

}