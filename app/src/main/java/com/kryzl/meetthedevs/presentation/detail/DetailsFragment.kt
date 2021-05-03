package com.kryzl.meetthedevs.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.base.presentation.setImageFromUrl
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import kotlinx.android.synthetic.main.details_fragment.view.*
import javax.inject.Inject

class DetailsFragment(private val developer: Developer): BaseFragment<DetailsViewModel>() {

    override val refreshViewModel: Boolean = true

    @Inject
    lateinit var imageHandler: RemoteImageHandler

    override fun getLayoutResourceId(): Int = R.layout.details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setDeveloper(developer)
        setViews(view)
        setListener(view)
    }

    private fun setViews(view: View) {
        viewModel.developerLiveData.observe(
            viewLifecycleOwner,
            Observer { developer ->
                developer.photoUrl?.let {
                    view.detail_image.setImageFromUrl(imageHandler, it)
                }
                view.detail_name.text = developer.name

                val developerInfo: MutableMap<String, String> = LinkedHashMap()

                developerInfo[getString(R.string.details_label_mobile_no)] = developer.mobileNo
                developerInfo[getString(R.string.details_label_email)] = developer.email
                developerInfo[getString(R.string.details_label_company_name)] = developer.companyName

                val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                view.details_recycler_view.layoutManager = layoutManager
                view.details_recycler_view.adapter = LabelValueAdapter(developerInfo)
            }
        )
    }

    private fun setListener(view: View) {
        view.details_edit_button.setOnClickListener {
            viewModel.editDetails(developer)
        }
        view.details_delete_button.setOnClickListener {
            viewModel.deleteDetails(developer)
        }
    }

}