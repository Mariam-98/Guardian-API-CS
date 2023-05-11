package com.example.weatherapics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapics.model.FilterEnum

class FilterAdapter(val itemClick: (FilterEnum) -> Unit) : RecyclerView.Adapter<FilterAdapter.GuardianFilterViewHolder>() {

    private var items = FilterEnum.values()

    private lateinit var context: Context
    private lateinit var inflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GuardianFilterViewHolder(inflater.inflate(R.layout.item_simple, parent, false))
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: GuardianFilterViewHolder, position: Int) = holder.bind(items[position])

    inner class GuardianFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val filterTextView: TextView = itemView.findViewById(R.id.titleTextView)

        init {
            itemView.setOnClickListener {
                itemClick(items[adapterPosition])
            }
        }

        fun bind(item: FilterEnum) {
            filterTextView.text = item.value
        }
    }
}