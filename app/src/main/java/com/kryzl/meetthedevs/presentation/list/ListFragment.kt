package com.kryzl.meetthedevs.presentation.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.base.presentation.BaseFragment
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import kotlinx.android.synthetic.main.list_fragment.view.*
import javax.inject.Inject

class ListFragment : BaseFragment<ListViewModel>(), DeveloperViewHolder.Listener {

    @Inject
    lateinit var imageHandler: RemoteImageHandler

    override fun getLayoutResourceId(): Int = R.layout.list_fragment

    override val refreshViewModel: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers(view)
        setListeners(view)
    }

    private fun setObservers(view: View) {
        viewModel.developers.observe(
            viewLifecycleOwner,
            Observer {
                prepareDevelopersList(it, view)
            }
        )
    }

    private fun setListeners(view: View) {
        view.add_developer.setOnClickListener {
            viewModel.addDeveloper()
        }
    }

    private fun prepareDevelopersList(developers: List<Developer>, view: View) {
        if (developers.isEmpty()) {
            view.list_label_no_data.visibility = View.VISIBLE
            view.list_developer_recycler_view.visibility = View.GONE
        } else {
            view.list_label_no_data.visibility = View.GONE
            val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            view.list_developer_recycler_view.layoutManager = layoutManager
            view.list_developer_recycler_view.adapter = DeveloperAdapter(this, imageHandler).apply {
                developersList = developers
            }
            view.list_developer_recycler_view.visibility = View.VISIBLE
        }

        view.list_progress_bar.visibility = View.GONE
    }

    override fun onClick(developer: Developer) {
        viewModel.showDetails(developer)
    }

}
