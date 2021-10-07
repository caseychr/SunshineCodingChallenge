package com.g0zi0.sunshinecodingchallenge

import androidx.lifecycle.Observer

/**
 * Represents a resource object.
 */
sealed class Resource<T> {

    /**
     * Represents a UI that responds to loading of a resource. These use cases (success, error, and loading)
     * apply to most use cases.
     */
    interface ResourceView<T> {
        fun showData(data: T)
        fun showLoading(isLoading: Boolean)
        fun showError(throwable: Throwable)
    }

    /**
     * Indicates the resources was successfully loaded
     *
     * @param data - the loaded resource value
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * Indicates the resource is currently being loaded
     */
    class Loading<T> : Resource<T>()

    /**
     * Indicates there was an error when trying to load the given resource
     *
     * @param error - the error that occurred while loading the resource
     */
    data class Error<T>(val error: Throwable) : Resource<T>()

}

/**
 * A generic resource observer that takes some boilerplate away by auto mapping a resource based
 * on its state to the corresponding ResourceView method.
 */
class ResourceViewObserver<T>(private val view: Resource.ResourceView<T>) : Observer<Resource<T>> {
    override fun onChanged(resource: Resource<T>?) {
        handleResourceChange(view, resource)
    }

}

/**
 * Applies a [Resource] to a [ResourceView].
 *
 * @param view the resource view to set a [Resource] to.
 * @param resource the resource to assign to the [ResourceView].
 */
private fun <T> handleResourceChange(view: Resource.ResourceView<T>, resource: Resource<T>?) {
    when(resource) {
        is Resource.Success -> {
            view.showLoading(isLoading = false)
            view.showData(data = resource.data)
        }
        is Resource.Loading -> {view.showLoading(true)}
        is Resource.Error -> {
            view.showLoading(isLoading = false)
            view.showError(throwable = resource.error)
        }
    }
}