package com.g0zi0.sunshinecodingchallenge.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g0zi0.sunshinecodingchallenge.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    protected val viewModelScope = CoroutineScope(Job() + Dispatchers.Main)

    /**
     * Helper extension function to simplify the common scenario of loading a resource from a suspend
     * function into a live data value.
     *
     * This sets the Resource to a initial state of loading before executing the suspending function
     * in the context of a coroutine.
     *
     * If a exception is thrown by the valueLoader for any reason, a Resource will be returned with that
     * exception.
     *
     * @receiver a instance of live data for a Resource<T> that is associated with a given resource.
     * @param T the target type being loaded wrapped with a Resource.
     * @param valueLoader the suspend function that provides a Resource value for the given live data.
     */
    fun <T> MutableLiveData<Resource<T>>.loadResource(valueLoader: suspend () -> Resource<T>) {
        this.loadResource(valueLoader, { it })
    }

    /**
     * Helper extension function to simplify the common scenario of loading a resource from a suspend
     * function into a live data value. This loads a [Resource] of [T] then maps that [Resource] to
     * [R].
     *
     * This sets the Resource to a initial state of loading before executing the suspending function
     * in the context of a coroutine.
     *
     * If a exception is thrown by the valueLoader for any reason, a [Resource.Error] will be returned
     * with that exception.
     *
     * @receiver a instance of live data for a Resource<T> that is associated with a given resource.
     * @param T the target type being loaded wrapped with a Resource.
     * @param valueLoader the suspend function that provides a Resource value for the given live data.
     * @param mapper a mapper function that will map [T] to [R].
     */
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