package com.example.vsuet.newsRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vsuet.R
import com.example.vsuet.startMenuFragment.newsMenuFragment.NewsFragmentDirections
import com.example.vsuet.startMenuFragment.newsMenuFragment.NewsItem

class NewsRecyclerViewAdapter(val navController: NavController) : RecyclerView.Adapter<NewsViewHolder>() {


    var data = listOf<NewsItem>()
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
        val itemUri = item.previewImage.toUri().buildUpon().scheme("https").build()
        holder.apply {
            Glide.with(newsPreviewImage.context)
                .load(itemUri)
                .into(newsPreviewImage)
            newsPreviewTitle.text = data[position].previewTitle
            newsPreviewLinkContainer.text = data[position].insideLink
            newsItemContainer.setOnClickListener {
                navController.navigate(NewsFragmentDirections.toNewsItem(data[position].previewTitle, data[position].insideLink))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.news_viewholder_layout, parent, false)
        return NewsViewHolder(view)
    }

}