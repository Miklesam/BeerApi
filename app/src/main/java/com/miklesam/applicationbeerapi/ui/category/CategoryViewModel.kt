package com.miklesam.applicationbeerapi.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miklesam.applicationbeerapi.data.BeerRepository

class CategoryViewModel @ViewModelInject constructor(
    private val repository: BeerRepository
) : ViewModel() {

    private val curretQuery = MutableLiveData(DEFAULT_CATEGORY_FOOD)

    val beers = curretQuery.switchMap { categoryFood ->
        repository.getSearchBeerResult(categoryFood).cachedIn(viewModelScope)
    }

    fun searchCategoryNeers(food: String) {
        curretQuery.value = food
    }

    companion object {
        private val DEFAULT_CATEGORY_FOOD = "chicken"
    }
}
