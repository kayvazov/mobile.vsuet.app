package com.example.vsuet.PointsRecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R

class PointViewHolder(pointTicket: View) : RecyclerView.ViewHolder(pointTicket) {

    val pointNumber : TextView = pointTicket.findViewById(R.id.pointNumber)

}