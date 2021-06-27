package com.kryzl.meetthedevs.presentation.addedit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.databinding.AddEditFragmentBinding
import com.kryzl.meetthedevs.domain.Developer

class AddEditFragment : BaseFragment<AddEditViewModel, AddEditFragmentBinding>() {

    var developer: Developer? = null

    override fun getLayoutResourceId(): Int = R.layout.add_edit_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        developer = arguments?.get(KEY_DEVELOPER) as Developer?
        setViews()
        setListeners()
        setObservers()
    }

    override val hasDataBinding: Boolean = true

    private fun setViews() {
        viewDataBinding?.apply {
            addEditSubmit.isEnabled = false
            addEditHeader.text = developer?.let {
                setDetails(developer!!)
                injectedContext.getString(R.string.action_edit)
            } ?: injectedContext.getString(R.string.action_add)
        }
    }

    private fun setDetails(developer: Developer) {
        viewDataBinding?.apply {
            fullNameEditText.setText(developer.name)
            viewModel.validateName(developer.name)
            mobileNoEditText.setText(developer.mobileNo)
            viewModel.validateMobile(developer.mobileNo)
            emailEditText.setText(developer.email)
            viewModel.validateEmail(developer.email)
            companyEditText.setText(developer.companyName)
            viewModel.validateCompany(developer.companyName)
        }
    }

    private fun setListeners() {
        viewDataBinding?.fullNameEditText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no action
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.validateName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no action
            }
        })

        viewDataBinding?.mobileNoEditText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no action
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.validateMobile(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no action
            }
        })

        viewDataBinding?.emailEditText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no action
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.validateEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no action
            }
        })

        viewDataBinding?.companyEditText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // no action
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.validateCompany(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no action
            }
        })

        viewDataBinding?.addEditSubmit?.setOnClickListener {
            if (developer != null) {
                viewDataBinding?.let {
                    developer!!.name = it.fullNameEditText.text.toString().trim()
                    developer!!.mobileNo = it.mobileNoEditText.text.toString().trim()
                    developer!!.email = it.emailEditText.text.toString().trim()
                    developer!!.companyName = it.companyEditText.text.toString().trim()
                    viewModel.updateDeveloper(developer!!)
                }
            } else {
                viewDataBinding?.let {
                    viewModel.addDeveloper(
                            Developer(
                                    name =  it.fullNameEditText.text.toString().trim(),
                                    mobileNo = it.mobileNoEditText.text.toString().trim(),
                                    email = it.emailEditText.text.toString().trim(),
                                    companyName = it.companyEditText.text.toString().trim()))
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.isValidName.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    viewDataBinding?.addEditFullname?.error =
                        resources.getString(R.string.input_error, "name")
                } else {
                    viewDataBinding?.addEditFullname?.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidMobile.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    viewDataBinding?.addEditMobileNo?.error =
                        resources.getString(R.string.input_error, "mobile number")
                } else {
                    viewDataBinding?.addEditMobileNo?.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidEmail.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    viewDataBinding?.addEditEmail?.error =
                        resources.getString(R.string.input_error, "email")
                } else {
                    viewDataBinding?.addEditEmail?.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidCompany.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    viewDataBinding?.addEditCompany?.error =
                        resources.getString(R.string.input_error, "company name")
                } else {
                    viewDataBinding?.addEditCompany?.isErrorEnabled = false
                }
            }
        )

        viewModel.allFieldsValid.observe(
            viewLifecycleOwner,
            Observer {
                viewDataBinding?.addEditSubmit?.isEnabled = it
            }
        )
    }

    companion object {
        const val KEY_DEVELOPER = "KEY_DEVELOPER"
    }

}
