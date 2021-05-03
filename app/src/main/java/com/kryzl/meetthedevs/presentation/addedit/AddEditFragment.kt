package com.kryzl.meetthedevs.presentation.addedit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.domain.Developer
import kotlinx.android.synthetic.main.add_edit_fragment.view.*

class AddEditFragment(private val developer: Developer? = null) : BaseFragment<AddEditViewModel>() {

    override fun getLayoutResourceId(): Int = R.layout.add_edit_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setListeners(view)
        setObservers(view)
    }

    private fun setViews(view: View) {
        view.add_edit_submit.isEnabled = false
        view.add_edit_header.text = developer?.let {
            view.full_name_edit_text.setText(developer.name)
            viewModel.validateName(developer.name)
            view.mobile_no_edit_text.setText(developer.mobileNo)
            viewModel.validateMobile(developer.mobileNo)
            view.email_edit_text.setText(developer.email)
            viewModel.validateEmail(developer.email)
            view.company_edit_text.setText(developer.companyName)
            viewModel.validateCompany(developer.companyName)
            injectedContext.getString(R.string.action_edit)
        } ?: injectedContext.getString(R.string.action_add)
    }

    private fun setListeners(view: View) {
        view.full_name_edit_text.addTextChangedListener(object : TextWatcher {
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

        view.mobile_no_edit_text.addTextChangedListener(object : TextWatcher {
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

        view.email_edit_text.addTextChangedListener(object : TextWatcher {
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

        view.company_edit_text.addTextChangedListener(object : TextWatcher {
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

        view.add_edit_submit.setOnClickListener {
            if (developer != null) {
                developer.name = view.full_name_edit_text.text.toString().trim()
                developer.mobileNo = view.mobile_no_edit_text.text.toString().trim()
                developer.email = view.email_edit_text.text.toString().trim()
                developer.companyName = view.company_edit_text.text.toString().trim()
                viewModel.updateDeveloper(developer)
            } else {
                viewModel.addDeveloper(
                    Developer(
                        name =  view.full_name_edit_text.text.toString().trim(),
                        mobileNo = view.mobile_no_edit_text.text.toString().trim(),
                        email = view.email_edit_text.text.toString().trim(),
                        companyName = view.company_edit_text.text.toString().trim()))
            }
        }
    }

    private fun setObservers(view: View) {
        viewModel.isValidName.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    view.add_edit_fullname.error = resources.getString(R.string.input_error, "name")
                } else {
                    view.add_edit_fullname.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidMobile.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    view.add_edit_mobile_no.error = resources.getString(R.string.input_error, "mobile number")
                } else {
                    view.add_edit_mobile_no.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidEmail.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    view.add_edit_email.error = resources.getString(R.string.input_error, "email")
                } else {
                    view.add_edit_email.isErrorEnabled = false
                }
            }
        )

        viewModel.isValidCompany.observe(
            viewLifecycleOwner,
            Observer {
                if (!it) {
                    view.add_edit_company.error = resources.getString(R.string.input_error, "company name")
                } else {
                    view.add_edit_company.isErrorEnabled = false
                }
            }
        )

        viewModel.allFieldsValid.observe(
            viewLifecycleOwner,
            Observer {
                view.add_edit_submit.isEnabled = it
            }
        )
    }

}