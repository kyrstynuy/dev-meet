package com.kryzl.meetthedevs.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kryzl.meetthedevs.R

class LabelValueAdapter constructor(detailsMap: Map<String, String>) :
    RecyclerView.Adapter<LabelValueViewHolder>() {

    private var labels: List<String> = emptyList()
    private var values: List<String> = emptyList()

    init {
        labels = detailsMap.keys.toList()
        values = detailsMap.values.toList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = labels.size

    override fun onBindViewHolder(holder: LabelValueViewHolder, position: Int) {
        holder.bind(labels[position], values[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelValueViewHolder {
        return LabelValueViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.label_value_layout, parent, false))
    }
}
