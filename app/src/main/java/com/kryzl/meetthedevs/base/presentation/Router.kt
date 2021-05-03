package com.kryzl.meetthedevs.base.presentation

import android.app.Activity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.Lifecycle
import timber.log.Timber
import java.lang.IllegalStateException

/**
 * Router
 */
class Router {

    private lateinit var activity: Activity
    private var container: ViewGroup? = null
    private var hasRootFragment = false
    private lateinit var rootTag: String
    private var fragmentManager: FragmentManager? = null

    /**
     * Pops current fragment
     */
    fun popCurrentFragment() {
        assumeAttached { fragmentManager ->
            val fragmentTagFrom = getTopFragmentTag(1, fragmentManager)
            val fragmentTagTo = getTopFragmentTag(2, fragmentManager)

            fragmentTagTo?.let {
                fragmentManager.beginTransaction().apply {
                    setMaxLifecycle(
                        fragmentManager.findFragmentByTag(it)!!,
                        Lifecycle.State.RESUMED
                    )
                    commit()
                }
            }

            Timber.d("Popping from: $fragmentTagFrom")
            fragmentManager.popBackStack()

            if (fragmentTagFrom == rootTag) {
                Timber.d("No root fragment")
                hasRootFragment = false
            }
        }
    }

    /**
     * Pops all fragments excluding root
     */
    fun popToRoot() {
        assumeAttached { fragmentManager ->
            if (!hasRootFragment) {
                return@assumeAttached
            }
            Timber.d("Popping to root $rootTag")

            with(fragmentManager) {
                beginTransaction().apply {
                    for (i in 1 until backStackEntryCount) {
                        setMaxLifecycle(
                            findFragmentByTag(getBackStackEntryAt(i).name)!!,
                            Lifecycle.State.CREATED
                        )
                    }

                    commit()
                }
                popBackStack(rootTag, 0)
            }
        }
    }

    /**
     * Push fragment to backstack. Sets this fragment as root if no root
     *
     * @param fragment the fragment
     * @param tag
     */
    fun pushFragment(fragment: BaseFragment<*>, tag: String? = null) {
        assumeAttached { fragmentManager ->
            val fragmentTag = tag ?: generateTag(fragment)
            Timber.d("Pushing fragment: $fragmentTag")
            fragmentManager.beginTransaction().apply {
                replace(container!!.id, fragment, fragmentTag)
                addToBackStack(fragmentTag)
                commit()
            }
            if (!hasRootFragment) {
                rootTag = fragmentTag
                hasRootFragment = true
            }
        }
    }

    /**
     * Set root fragment. Pops all previously existing fragments.
     *
     * @param fragment the fragment
     */
    fun setRoot(fragment: BaseFragment<*>) {
        assumeAttached { fragmentManager ->
            if (hasRootFragment) {
                fragmentManager.popBackStackImmediate(rootTag, POP_BACK_STACK_INCLUSIVE)
            }

            rootTag = generateTag(fragment)
            Timber.d("Setting root: $rootTag")
            pushFragment(fragment)
            hasRootFragment = true
        }
    }

    /**
     * Passes back action to fragments
     */
    fun handleBack(): Boolean {
        assumeAttached { fragmentManager ->
            val fragmentTag = getTopFragmentTag(1, fragmentManager)
            val topFragment = fragmentManager.findFragmentByTag(fragmentTag) as? BaseFragment<*>?
            when (topFragment?.handleBack()) {
                true -> Timber.d("$fragmentTag did handle back")
                false -> {
                    Timber.d("$fragmentTag did not handle back")
                    popCurrentFragment()
                }
                null -> Timber.e("$fragmentTag not in stack")
            }
        }

        return true
    }

    /**
     * Pops to a specific tag
     *
     * @param tag [BaseFragment]'s tag
     * @param hasPendingFragmentTransactions this will cap the fragment's lifecycle to CREATED state.
     *        Set to true if another fragment transaction will run immediately after this.
     */
    fun popToTag(tag: String, hasPendingFragmentTransactions: Boolean = false) {
        assumeAttached { fragmentManager ->
            if (hasPendingFragmentTransactions) {
                fragmentManager.findFragmentByTag(tag)?.let {
                    fragmentManager.beginTransaction().apply {
                        setMaxLifecycle(it, Lifecycle.State.CREATED)
                        commit()
                    }
                }
            }

            fragmentManager.popBackStack(tag, 0)
        }
    }

    /**
     * Attaches to [Activity]. This does not check if the router was previously attached.
     *
     * @param activity
     * @param container
     */
    fun attachRouter(activity: Activity, container: ViewGroup) {
        this.activity = activity
        this.container = container
        this.fragmentManager = (activity as AppCompatActivity).supportFragmentManager
    }

    private fun assumeAttached(block: (fragmentManager: FragmentManager) -> Unit) {
        if (fragmentManager == null) {
            throw IllegalStateException("Router must be attached")
        } else {
            block.invoke(fragmentManager!!)
        }
    }

    private fun generateTag(fragment: BaseFragment<*>): String =
        fragment.javaClass.simpleName

    private fun getTopFragmentTag(index: Int, fragmentManager: FragmentManager) =
        with(fragmentManager) {
            if (backStackEntryCount >= index) {
                getBackStackEntryAt(backStackEntryCount - index).name
            } else {
                null
            }
        }
}
