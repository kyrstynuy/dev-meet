package com.kryzl.meetthedevs.presentation.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.label_value_layout.view.*

class LabelValueViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(label: String, value: String) {
        itemView.label_holder.text = label
        itemView.value_holder.text = value
    }
}
