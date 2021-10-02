package com.g0zi0.sunshinecodingchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g0zi0.sunshinecodingchallenge.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    protected val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    fun <T> MutableLiveData<Resource<T>>.loadResource(valueLoader: suspend () -> Resource<T>) {
        this.loadResource(valueLoader, { it })
    }

    fun <T, R> MutableLiveData<R>.loadResource(valueLoader: suspend () -> Resource<T>, mapper: (Resource<T>) -> R) {
        val resourceLiveData = this
        viewModelScope.launch {
            resourceLiveData.value = mapper(Resource.Loading())
            try {
                resourceLiveData.value = mapper(valueLoader())
            } catch (error: Exception) {
                resourceLiveData.value = mapper(Resource.Error(error))
            }
        }
    }
}