package com.kryzl.meetthedevs.presentation.addedit

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kryzl.meetthedevs.base.functional.CoroutineContextProvider
import com.kryzl.meetthedevs.base.functional.isValidEmail
import com.kryzl.meetthedevs.base.functional.isValidMobile
import com.kryzl.meetthedevs.base.functional.isValidName
import com.kryzl.meetthedevs.base.presentation.BaseViewModel
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.usecase.AddDeveloper
import com.kryzl.meetthedevs.usecase.UpdateDeveloper
import javax.inject.Inject

class AddEditViewModel @Inject constructor(
    private val updateDeveloper: UpdateDeveloper,
    private val addDeveloper: AddDeveloper,
    private val coroutineContextProvider: CoroutineContextProvider
): BaseViewModel() {

    val isValidName: MutableLiveData<Boolean> = MutableLiveData()
    val isValidMobile: MutableLiveData<Boolean> = MutableLiveData()
    val isValidEmail: MutableLiveData<Boolean> = MutableLiveData()
    val isValidCompany: MutableLiveData<Boolean> = MutableLiveData()
    val allFieldsValid: LiveData<Boolean> = Transformations.switchMap(isValidName) { isValidNameValue ->
        Transformations.switchMap(isValidMobile) { isValidMobileValue ->
            Transformations.switchMap(isValidEmail) { isValidEmailValue ->
                Transformations.map(isValidCompany) { isValidCompanyValue ->
                    isValidNameValue && isValidEmailValue && isValidMobileValue && isValidCompanyValue
                }
            }
        }
    }

    fun validateName(name: String) {
        isValidName.value = name.trim().isValidName()
    }

    fun validateMobile(mobileNo: String) {
        isValidMobile.value = mobileNo.trim().isValidMobile()
    }

    fun validateEmail(email: String) {
        isValidEmail.value = email.trim().isValidEmail()
    }

    fun validateCompany(company: String) {
        isValidCompany.value = company.trim().isNotBlank()
    }

    fun updateDeveloper(developer: Developer) {
        updateDeveloper.request = developer
        updateDeveloper.invoke(
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

    fun addDeveloper(developer: Developer) {
        addDeveloper.request = developer
        addDeveloper.invoke(
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