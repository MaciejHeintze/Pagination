package com.gaminidsystems.pagination

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class RecyclerAdapter(
    context: Context,
    listRecyclerItem: List<Any>
) :
    RecyclerView.Adapter<ViewHolder>() {
    private val context: Context
    private val listRecyclerItem: List<Any>

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val nct_id: TextView
        val brief_title: TextView
        val phase: TextView
        val overall_status: TextView
        val source: TextView
        val study_type: TextView

        init {
            nct_id = itemView.findViewById(R.id.npi_number_text_view)
            brief_title = itemView.findViewById(R.id.official_title)
            phase = itemView.findViewById(R.id.phase_Text_view)
            overall_status = itemView.findViewById(R.id.overall_status)
            source = itemView.findViewById(R.id.source_text_view)
            study_type = itemView.findViewById(R.id.study_type_text_view)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return when (i) {
            TYPE -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
            else -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val viewType = getItemViewType(i)
        when (viewType) {
            TYPE -> {
                val itemViewHolder =
                    viewHolder as ItemViewHolder
                val studies: Study = listRecyclerItem[i] as Study
                itemViewHolder.nct_id.text = "NPI Number: ${studies.nct_id}"
                itemViewHolder.brief_title.text = "Brief title: ${studies.brief_title}"
                itemViewHolder.phase.text = "Phase: ${studies.phase}"
                itemViewHolder.overall_status.text = "Status: ${studies.overall_status}"
                itemViewHolder.source.text = "Source: ${studies.source}"
                itemViewHolder.study_type.text = "Study type: ${studies.study_type}"

            }
            else -> {
                val itemViewHolder =
                    viewHolder as ItemViewHolder
                val studies: Study = listRecyclerItem[i] as Study
                itemViewHolder.nct_id.text = "NPI Number: ${studies.nct_id}"
                itemViewHolder.brief_title.text = "Brief title: ${studies.brief_title}"
                itemViewHolder.phase.text = "Phase: ${studies.phase}"
                itemViewHolder.overall_status.text = "Status: ${studies.overall_status}"
                itemViewHolder.source.text = "Source: ${studies.source}"
                itemViewHolder.study_type.text = "Study type: ${studies.study_type}"
            }
        }
    }

    override fun getItemCount(): Int {
        return listRecyclerItem.size
    }

    companion object {
        private const val TYPE = 1
    }

    init {
        this.context = context
        this.listRecyclerItem = listRecyclerItem
    }
}