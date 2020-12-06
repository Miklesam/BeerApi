package com.miklesam.applicationbeerapi

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.applicationbeerapi.di.Beer

class RandomViewModel @ViewModelInject
constructor(private val repository: BeerRepository) :
    ViewModel() {
    val progressBarEvent: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun getRandomBeer() {
        progressBarEvent.postValue(true)
        val tt = repository.getRandomBeer()
        when (tt) {
            is ApiResult.Success -> {
                Log.w("Beer", "Sucess VM")
                progressBarEvent.postValue(false)
            }
            is ApiResult.Failure -> {
                Log.w("Beer", "Failure VM")
                progressBarEvent.postValue(false)
            }
        }
    }

}