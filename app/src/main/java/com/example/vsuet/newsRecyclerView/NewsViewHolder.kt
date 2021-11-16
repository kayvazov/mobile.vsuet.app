package com.example.vsuet.newsRecyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R

class NewsViewHolder(newsTicket: View) : RecyclerView.ViewHolder(newsTicket) {

    val newsPreviewImage: ImageView = itemView.findViewById(R.id.newsPreviewImage)
    val newsText: TextView = itemView.findViewById(R.id.newsText)

}