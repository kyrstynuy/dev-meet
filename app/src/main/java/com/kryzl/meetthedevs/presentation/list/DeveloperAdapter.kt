package com.kryzl.meetthedevs.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R
import com.kryzl.meetthedevs.domain.Developer
import com.kryzl.meetthedevs.image.RemoteImageHandler

class DeveloperAdapter(
    private val listener: DeveloperViewHolder.Listener,
    private val imageHandler: RemoteImageHandler
) :
    RecyclerView.Adapter<DeveloperViewHolder>() {

    var developersList: List<Developer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        holder.bind(
            developersList[position],
            listener,
            imageHandler
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        return DeveloperViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_developer_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return developersList.size
    }
}
