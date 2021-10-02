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
 * A resource view observer that handles the consumption of [SingleUseContent]. This allows us to have
 * emissions that only emit once. This "only once" emission is useful for when you perform "destructive"
 * actions from certain components (think navigating to a fragment from an activity in [Observer.onChanged]).
 * In this scenario, the fragment manager handles keeping the fragment back stack intact and re-emission
 * would cause an additional navigation to take place.
 *
 * By default, errors will be re-emitted (meaning if a error happens and the user rotates the screen,
 * the error will re-emit to any observers). If you want errors to only emit once (say you do something
 * destructive on error, like navigation) then pass in false for [shouldReEmitErrors].
 *
 * [SingleUseContent] is useful for UI reactions to emissions that are destructive or should only be
 * shown once (think navigation or error popups).
 */
/*class SingleUseResourceViewObserver<T>(private val view: Resource.ResourceView<T>, private val shouldReEmitErrors: Boolean = true):
    Observer<SingleUseContent<Resource<T>>> {
    override fun onChanged(event: SingleUseContent<Resource<T>>?) {
        if(shouldReEmitErrors && event?.peekContent() is Resource.Error) {
            handleResourceChange(view, event.peekContent())
        }
        event?.getContentIfNotConsumed()?.let { handleResourceChange(view, it) }
    }
}*/

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