package com.kryzl.meetthedevs.base.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel>: DaggerFragment() {

    @Inject
    protected lateinit var viewModel: T

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var injectedContext: Context

    /** Set to true if view model
     * should be refreshed every time view is created **/
    protected open val refreshViewModel: Boolean = false

    private var injected = false

    abstract fun getLayoutResourceId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResourceId(), container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!injected) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModel.javaClass)
            injected = true
        } else if (refreshViewModel) {
            viewModelStore.clear()
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModel.javaClass)
        }
        listenForNavigationRequests()
        setFailureObserver()
    }

    /** No error handling for now :(
     * Ideally, I'd have retry features, sadly couldn't fit in time
     */
    private fun setFailureObserver() {
        viewModel.failure.observe(
            viewLifecycleOwner,
            Observer {
                Toast.makeText(context, R.string.error_fetching_data, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun listenForNavigationRequests() {
        viewModel.nextFragment.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    if (it.shouldPopCurrentFragment) {
                        router.popCurrentFragment()
                    }
                    router.pushFragment(it.fragment, it.tag)
                    viewModel.nextFragment.value = null
                }
            }
        )

        viewModel.backToHome.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    router.popToRoot()
                }
            }
        )

        viewModel.exit.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    router.popCurrentFragment()
                }
            }
        )
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }

    open fun handleBack(): Boolean {
        return false
    }

}
