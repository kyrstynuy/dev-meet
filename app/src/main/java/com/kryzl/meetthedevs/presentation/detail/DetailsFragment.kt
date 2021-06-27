package com.kryzl.meetthedevs.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.databinding.DetailsFragmentBinding
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import com.kryzl.meetthedevs.presentation.addedit.AddEditFragment
import javax.inject.Inject

class DetailsFragment: BaseFragment<DetailsViewModel, DetailsFragmentBinding>() {

    lateinit var developer: Developer

    override val refreshViewModel: Boolean = true

    @Inject
    lateinit var imageHandler: RemoteImageHandler

    override fun getLayoutResourceId(): Int = R.layout.details_fragment

    override val hasDataBinding: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        developer = requireArguments().get(AddEditFragment.KEY_DEVELOPER) as Developer
        viewModel.setDeveloper(developer)
        setViews()
        setListener()
    }

    private fun setViews() {
        viewModel.developerLiveData.observe(
            viewLifecycleOwner,
            Observer { developer ->
                val developerInfo: MutableMap<String, String> = LinkedHashMap()

                developerInfo[getString(R.string.details_label_mobile_no)] = developer.mobileNo
                developerInfo[getString(R.string.details_label_email)] = developer.email
                developerInfo[getString(R.string.details_label_company_name)] = developer.companyName

                viewDataBinding?.apply {
                    this.imageHandler = this@DetailsFragment.imageHandler
                    this.imageUrl = developer.photoUrl ?: ""

                    this.detailName.setText(developer.name)

                    this.detailsLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    this.detailsMap = developerInfo
                }
            }
        )
    }

    private fun setListener() {
        viewDataBinding?.detailsEditButton?.setOnClickListener {
            viewModel.editDetails(developer)
        }

        viewDataBinding?.detailsDeleteButton?.setOnClickListener {
            viewModel.deleteDetails(developer)
        }
    }

    companion object {
        const val KEY_DEVELOPER = "KEY_DEVELOPER"
    }

}
