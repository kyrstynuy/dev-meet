package com.kryzl.meetthedevs.presentation.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.databinding.ListFragmentBinding
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import kotlinx.android.synthetic.main.list_fragment.view.*
import javax.inject.Inject

class ListFragment : BaseFragment<ListViewModel, ListFragmentBinding>(), DeveloperViewHolder.Listener {

    @Inject
    lateinit var imageHandler: RemoteImageHandler

    override fun getLayoutResourceId(): Int = R.layout.list_fragment

    override val refreshViewModel: Boolean
        get() = true

    override val hasDataBinding: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.developers.observe(
            viewLifecycleOwner,
            Observer {
                prepareDevelopersList(it)
            }
        )
    }

    private fun setListeners() {
        viewDataBinding?.addDeveloper?.setOnClickListener {
            viewModel.addDeveloper()
        }
    }

    private fun prepareDevelopersList(developers: List<Developer>) {
        viewDataBinding?.apply {
            if (developers.isEmpty()) {
                listLabelNoData.visibility = View.VISIBLE
            } else {
                listLabelNoData.visibility = View.GONE
            }

            developersLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            imageHandler = this@ListFragment.imageHandler
            developerListener = this@ListFragment
            this.developers = developers

            listProgressBar.visibility = View.GONE
        }
    }

    override fun onClick(developer: Developer) {
        viewModel.showDetails(developer)
    }

}
