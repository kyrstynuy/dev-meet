package com.kryzl.meetthedevs.presentation.detail

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kryzl.meetthedevs.base.functional.CoroutineContextProvider
import com.kryzl.meetthedevs.base.presentation.BaseViewModel
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.presentation.addedit.AddEditFragment
import com.kryzl.meetthedevs.usecase.DeleteDeveloper
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val deleteDeveloper: DeleteDeveloper,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    val developerLiveData: MutableLiveData<Developer> = MutableLiveData()

    fun setDeveloper(developer: Developer) {
        developerLiveData.value = developer
    }

    fun editDetails(developer: Developer) {
        navigate(AddEditFragment(developer))
    }

    fun deleteDetails(developer: Developer) {
        deleteDeveloper.request = developer
        deleteDeveloper.invoke(
            scope = viewModelScope,
            coroutineContextProvider = coroutineContextProvider,
            onResult = {
                it.either({ failure ->
                    this.failure.postValue(failure)
                }, {
                    Handler(Looper.getMainLooper()).post {
                        exit()
                    }
                })
            }
        )
    }

}
