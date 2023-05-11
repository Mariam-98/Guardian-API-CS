package com.example.weatherapics

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.weatherapics.data.SearchResultDto
import java.text.SimpleDateFormat
import java.util.Locale

class GuardianResultAdapter(val itemClick: (SearchResultDto) -> Unit) : RecyclerView.Adapter<GuardianResultAdapter.WeatherViewHolder>() {

    private lateinit var context: Context
    private var resultList = mutableListOf<SearchResultDto?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeatherList(resultList: List<SearchResultDto?>) {
        this.resultList.clear()
        this.resultList.addAll(resultList)
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_guardian_recycler, parent, false))
    }

    override fun getItemCount(): Int = resultList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) = holder.bind(resultList[position])

    inner class WeatherViewHolder(itemView: View) : ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                resultList[adapterPosition]?.let { it1 -> itemClick(it1) }
            }
        }

        private val weatherImageView: ImageView = itemView.findViewById(R.id.weatherImageView)
        private val sectionNameTextView: TextView = itemView.findViewById(R.id.sectionNameTextView)
        private val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)
        private val webPublicationDateTextView: TextView = itemView.findViewById(R.id.webPublicationDateTextView)

        fun bind(result: SearchResultDto?) {

            Glide.with(context).load(result?.fields?.thumbnail).centerCrop().into(weatherImageView)
            sectionNameTextView.text = result?.sectionName
            bodyTextView.text = result?.fields?.body?.let { HtmlCompat.fromHtml(it, FROM_HTML_MODE_LEGACY) }
            webPublicationDateTextView.text = result?.webPublicationDate?.let { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(it)?.toString() ?: return }
        }
    }
}