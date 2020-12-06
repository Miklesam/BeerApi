package com.miklesam.applicationbeerapi

import android.util.Log
import androidx.lifecycle.LiveData
import com.miklesam.applicationbeerapi.di.Beer
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Result

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

}