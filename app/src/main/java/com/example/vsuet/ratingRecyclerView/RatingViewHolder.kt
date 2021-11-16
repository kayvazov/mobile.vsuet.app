package com.example.vsuet.ratingRecyclerView

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R

class RatingViewHolder(ratingTicket: View) : RecyclerView.ViewHolder(ratingTicket) {

    val disciplineName : TextView = ratingTicket.findViewById(R.id.disciplineName)
    val disciplineType : TextView = ratingTicket.findViewById(R.id.disciplineType)
    val disciplinePoints : TextView = ratingTicket.findViewById(R.id.disciplinePoints)
    val pointsRecycler : RecyclerView = ratingTicket.findViewById(R.id.pointsRecycler)
    val disciplineInfo: Button = ratingTicket.findViewById(R.id.disciplineInfo)

}