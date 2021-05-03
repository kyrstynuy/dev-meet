package com.kryzl.meetthedevs.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kryzl.meetthedevs.base.functional.CoroutineContextProvider
import com.kryzl.meetthedevs.base.presentation.BaseViewModel
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.presentation.addedit.AddEditFragment
import com.kryzl.meetthedevs.presentation.detail.DetailsFragment
import com.kryzl.meetthedevs.usecase.GetDevelopers
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getDevelopers: GetDevelopers,
    private val coroutineContextProvider: CoroutineContextProvider
) : BaseViewModel() {

    val developers: MutableLiveData<List<Developer>> = MutableLiveData()
    init {
        getDevelopers()
    }

    private fun getDevelopers() {
        getDevelopers.invoke(
            scope = viewModelScope,
            coroutineContextProvider = coroutineContextProvider,
            onResult = {
                it.either({ failure ->
                    this.failure.postValue(failure)
                }, { developers ->
                    this.developers.postValue(developers)
                })
            }
        )
    }

    fun showDetails(developer: Developer) {
        navigate(DetailsFragment(developer))
    }

    fun addDeveloper() {
        navigate(AddEditFragment())
    }

}