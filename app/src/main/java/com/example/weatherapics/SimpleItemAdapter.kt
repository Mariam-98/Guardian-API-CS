package com.example.weatherapics

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleItemAdapter(val itemClick: (String) -> Unit) : RecyclerView.Adapter<SimpleItemAdapter.DefaultViewHolder>() {

    private var items = mutableListOf<String>()

    private lateinit var context: Context
    private lateinit var inflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(inflater.inflate(R.layout.item_bottom_sheet_dialog, parent, false))
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) = holder.bind(items[position])

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClick(items[adapterPosition])
            }
        }

        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(item: String) {
            titleTextView.text = item
        }
    }
}