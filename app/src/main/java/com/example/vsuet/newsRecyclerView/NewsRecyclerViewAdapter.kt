package com.example.vsuet.newsRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vsuet.API.NewsPost
import com.example.vsuet.R

class NewsRecyclerViewAdapter : RecyclerView.Adapter<NewsViewHolder>() {


    var data = listOf<NewsPost>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = data[position]
        if(item.attachments?.isNotEmpty() == true && item.attachments[0].photo.sizes.isNotEmpty()) {
            val itemUri = item.attachments[0].photo.sizes[0].url
            holder.apply {
                Glide.with(newsPreviewImage.context)
                    .load(itemUri)
                    .into(newsPreviewImage)
            }
        }
        holder.apply {
            newsText.text = item.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.news_viewholder_layout, parent, false)
        return NewsViewHolder(view)
    }

}