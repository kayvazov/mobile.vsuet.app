package com.example.prepodsearch.listViewAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.prepodsearch.R

class ListViewAdapter : BaseAdapter() {

    var data = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, container: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(container?.context)
        val listViewItem = layoutInflater.inflate(R.layout.list_view_item, container, false)

        listViewItem.findViewById<TextView>(R.id.textContainer).text = data[position].toString()


        return listViewItem

    }


}