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

    private val mIsValidName: MutableLiveData<Boolean> = MutableLiveData()
    private val mIsValidMobile: MutableLiveData<Boolean> = MutableLiveData()
    private val mIsValidEmail: MutableLiveData<Boolean> = MutableLiveData()
    private val mIsValidCompany: MutableLiveData<Boolean> = MutableLiveData()

    val isValidName: LiveData<Boolean> = mIsValidName
    val isValidMobile: MutableLiveData<Boolean> = mIsValidMobile
    val isValidEmail: MutableLiveData<Boolean> = mIsValidEmail
    val isValidCompany: MutableLiveData<Boolean> = mIsValidCompany

    val allFieldsValid: LiveData<Boolean> = Transformations.switchMap(mIsValidName) { isValidNameValue ->
        Transformations.switchMap(mIsValidMobile) { isValidMobileValue ->
            Transformations.switchMap(mIsValidEmail) { isValidEmailValue ->
                Transformations.map(mIsValidCompany) { isValidCompanyValue ->
                    isValidNameValue && isValidEmailValue && isValidMobileValue && isValidCompanyValue
                }
            }
        }
    }

    fun validateName(name: String) {
        mIsValidName.value = name.trim().isValidName()
    }

    fun validateMobile(mobileNo: String) {
        mIsValidMobile.value = mobileNo.trim().isValidMobile()
    }

    fun validateEmail(email: String) {
        mIsValidEmail.value = email.trim().isValidEmail()
    }

    fun validateCompany(company: String) {
        mIsValidCompany.value = company.trim().isNotBlank()
    }

    fun updateDeveloper(developer: Developer) {
        updateDeveloper.request = developer
        updateDeveloper.invoke(
            scope = viewModelScope,
            coroutineContextProvider = coroutineContextProvider,
            onResult = {
                it.either({ failure ->
                    this.mFailure.postValue(failure)
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
                    this.mFailure.postValue(failure)
                }, {
                    Handler(Looper.getMainLooper()).post {
                        exit()
                    }

                })
            }
        )
    }

}
