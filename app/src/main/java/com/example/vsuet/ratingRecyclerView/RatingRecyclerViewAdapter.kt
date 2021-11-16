package com.example.vsuet.ratingRecyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.RatingItem
import com.example.vsuet.PointsRecyclerView.PointsRecyclerViewAdapter
import com.example.vsuet.R
import com.example.vsuet.disciplineInfoDialogFragment.DisciplineInfoDialogFragment
import com.example.vsuet.pointDialogFragment.PointDialogFragment

class RatingRecyclerViewAdapter(val context: Context, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<RatingViewHolder>() {

    var data = listOf<RatingItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val item = data[position]

        holder.apply {
            val adapter = PointsRecyclerViewAdapter(fragmentManager)
            adapter.data = item.upgradedRating
            pointsRecycler.adapter = adapter
            pointsRecycler.layoutManager = LinearLayoutManager(context)
            disciplineName.text = item.lesson.name
            disciplineType.text = item.lesson.type
            disciplinePoints.text = "Итоговый рейтинг: " + item.value[26]
            disciplineInfo.setOnClickListener {
                val dialog = DisciplineInfoDialogFragment(item.lesson.information)
                dialog.show(fragmentManager, "PointInfo")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rating_ticket, parent, false)
        return RatingViewHolder(view)
    }

}