package com.miklesam.applicationbeerapi.paging

import androidx.paging.PagingSource
import com.miklesam.applicationbeerapi.models.Beer
import com.miklesam.applicationbeerapi.network.BeerApi
import java.io.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class BeerPagingSource(private val beerApi: BeerApi, private val query: String) :
    PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = beerApi.beerWithFood(position, params.loadSize, query)
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
