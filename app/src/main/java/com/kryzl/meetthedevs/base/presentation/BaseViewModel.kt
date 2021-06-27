package com.kryzl.meetthedevs.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryzl.meetthedevs.base.exception.Failure

/**
 * Credits to Fernando Cejas
 * Source: https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/platform/BaseViewModel.kt
 *
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {
    protected val mFailure: MutableLiveData<Failure> = MutableLiveData()
    private val mNextFragment = MutableLiveData<NavigationRequest>()
    private val mExit = MutableLiveData<Boolean>()
    private val mBackToHome = MutableLiveData<Boolean>()

    val failure: LiveData<Failure> = mFailure
    val nextFragment: LiveData<NavigationRequest> = mNextFragment
    val exit: LiveData<Boolean> = mExit
    val backToHome: LiveData<Boolean> = mBackToHome

    open fun handleFailure(failure: Failure) {
        this.mFailure.value = failure
    }

    protected fun navigate(
        fragment: BaseFragment<*, *>,
        shouldPopCurrentFragment: Boolean = false,
        tag: String? = null
    ) {
        this.mNextFragment.value = NavigationRequest(
            fragment,
            shouldPopCurrentFragment, tag ?: fragment.javaClass.simpleName
        )
    }

    fun exit() {
        mExit.value = true
    }

    fun clearNextFragment() {
        mNextFragment.value = null
    }

    open fun backToHome() {
        mBackToHome.value = true
    }

    class NavigationRequest(
        val fragment: BaseFragment<*, *>,
        val shouldPopCurrentFragment: Boolean,
        val tag: String
    )
}
