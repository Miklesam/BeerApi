package com.miklesam.applicationbeerapi

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RandomViewModel @ViewModelInject
constructor(private val repository: BeerRepository) :
    ViewModel() {
    val progressBarEvent: MutableLiveData<Boolean> = MutableLiveData()
    val beer: MutableLiveData<Beer> = MutableLiveData()
    val toastEvent: SingleLiveEvent<String> = SingleLiveEvent()

    suspend fun getRandomBeer() {
        progressBarEvent.postValue(true)
        val tt = repository.getRandomBeer()
        when (tt) {
            is ApiResult.Success -> {
                Log.w("Beer", "Sucess VM")
                progressBarEvent.postValue(false)
                beer.postValue(tt.data?.get(0))
            }
            is ApiResult.Failure -> {
                Log.w("Beer", "Failure VM")
                progressBarEvent.postValue(false)
                toastEvent.postValue(tt.exception?.localizedMessage)
            }
        }
    }

}