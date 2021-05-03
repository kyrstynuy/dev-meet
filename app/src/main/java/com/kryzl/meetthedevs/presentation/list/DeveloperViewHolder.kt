package com.kryzl.meetthedevs.presentation.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.base.presentation.setImageFromUrl
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler
import kotlinx.android.synthetic.main.list_developer_viewholder.view.*

class DeveloperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        developer: Developer,
        listener: Listener,
        imageHandler: RemoteImageHandler
    ) = with(itemView) {
        developer.photoUrl?.let {
            itemView.developer_image.setImageFromUrl(imageHandler, it)
        }
        itemView.developer_name.text = developer.name
        itemView.developer_company.text = developer.companyName

        itemView.setOnClickListener {
            listener.onClick(developer)
        }
    }

    interface Listener {
        fun onClick(developer: Developer)
    }
}
