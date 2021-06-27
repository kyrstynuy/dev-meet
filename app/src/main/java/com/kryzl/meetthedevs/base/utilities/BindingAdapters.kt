package com.kryzl.meetthedevs.base.utilities

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.base.presentation.setImageFromUrl
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import com.kryzl.meetthedevs.presentation.detail.LabelValueAdapter
import com.kryzl.meetthedevs.presentation.list.DeveloperAdapter
import com.kryzl.meetthedevs.presentation.list.DeveloperViewHolder

object BindingAdapters {

    @BindingAdapter("imageUrl", "imageHandler", requireAll = false)
    @JvmStatic
    fun loadImage(imageView: ImageView,
                  imageUrl: String,
                  imageHandler: RemoteImageHandler) {
        imageView.setImageFromUrl(imageHandler, imageUrl)
    }

    @BindingAdapter("layoutManager", "labelValueMap", requireAll = false)
    @JvmStatic
    fun loadLabelValue(labelValueRecyclerView: RecyclerView,
                       layoutManager: LinearLayoutManager,
                       map: Map<String, String>) {
        labelValueRecyclerView.layoutManager = layoutManager
        labelValueRecyclerView.adapter = LabelValueAdapter(map)
    }

    @BindingAdapter("layoutManager", "developerListener", "imageHandler", "developers", requireAll = false)
    @JvmStatic
    fun loadList(listRecyclerView: RecyclerView,
                 layoutManager: LinearLayoutManager? = null,
                 listener: DeveloperViewHolder.Listener? = null,
                 imageHandler: RemoteImageHandler? = null,
                 developers: List<Developer>? = null) {
        if (developers?.isNotEmpty() == true) {
            listRecyclerView.layoutManager = layoutManager
            listRecyclerView.adapter = DeveloperAdapter(listener!!, imageHandler!!).apply {
                developersList = developers
            }

            listRecyclerView.visibility = View.VISIBLE
        } else {
            listRecyclerView.visibility = View.GONE
        }
    }

}
