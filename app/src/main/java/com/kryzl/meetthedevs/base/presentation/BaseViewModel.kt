package com.kryzl.meetthedevs.base.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryzl.meetthedevs.base.exception.Failure
import javax.inject.Inject

/**
 * Credits to Fernando Cejas
 * Source: https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/platform/BaseViewModel.kt
 *
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var context: Context

    val failure: MutableLiveData<Failure> = MutableLiveData()
    val nextFragment = MutableLiveData<NavigationRequest>()
    val exit = MutableLiveData<Boolean>()
    val backToHome = MutableLiveData<Boolean>(false)

    open fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    protected fun navigate(
        fragment: BaseFragment<*>,
        shouldPopCurrentFragment: Boolean = false,
        tag: String? = null
    ) {
        this.nextFragment.value = NavigationRequest(
            fragment,
            shouldPopCurrentFragment, tag ?: fragment.javaClass.simpleName
        )
    }

    fun exit() {
        exit.value = true
    }

    open fun backToHome() {
        backToHome.value = true
    }

    class NavigationRequest(
        val fragment: BaseFragment<*>,
        val shouldPopCurrentFragment: Boolean,
        val tag: String
    )
}
