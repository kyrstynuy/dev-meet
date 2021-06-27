package com.kryzl.meetthedevs.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kryzl.meetthedevs.base.functional.CoroutineContextProvider
import com.kryzl.meetthedevs.base.presentation.BaseViewModel
import com.kryzl.meetthedevs.base.presentation.withArgs
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.presentation.addedit.AddEditFragment
import com.kryzl.meetthedevs.presentation.detail.DetailsFragment
import com.kryzl.meetthedevs.usecase.GetDevelopers
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getDevelopers: GetDevelopers,
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel() {

    private val mDevelopers: MutableLiveData<List<Developer>> = MutableLiveData()
    val developers: LiveData<List<Developer>> = mDevelopers

    init {
        getDevelopers()
    }

    private fun getDevelopers() {
        getDevelopers.invoke(
            scope = viewModelScope,
            coroutineContextProvider = coroutineContextProvider,
            onResult = {
                it.either({ failure ->
                    this.mFailure.postValue(failure)
                }, { developers ->
                    this.mDevelopers.postValue(developers)
                })
            }
        )
    }

    fun showDetails(developer: Developer) {
        navigate(DetailsFragment().withArgs {
            putParcelable(DetailsFragment.KEY_DEVELOPER, developer)
        })
    }

    fun addDeveloper() {
        navigate(AddEditFragment())
    }

}
