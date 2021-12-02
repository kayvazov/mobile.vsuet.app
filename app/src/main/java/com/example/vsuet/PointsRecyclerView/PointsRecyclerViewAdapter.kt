package com.example.vsuet.PointsRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.UpgradedRatingItem
import com.example.vsuet.R
import com.example.vsuet.pointDialogFragment.PointDialogFragment

class PointsRecyclerViewAdapter(private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<PointViewHolder>() {

    var data = listOf<UpgradedRatingItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val item = data[position]

        holder.apply {
            pointNumber.apply {
                text = item.total.name + " - " + item.total.score
                setOnClickListener {
                    val dialog = PointDialogFragment(item.items)
                    dialog.show(fragmentManager, "PointInfo")
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.point_ticket, parent, false)
        return PointViewHolder(view)
    }

}