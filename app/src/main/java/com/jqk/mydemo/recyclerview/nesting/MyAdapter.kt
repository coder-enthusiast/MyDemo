package com.jqk.mydemo.recyclerview.nesting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder> {
    val context: Context
    val datas: ArrayList<String>

    constructor(context: Context, datas: ArrayList<String>) : super() {
        this.context = context
        this.datas = datas
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview1, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.title.text = datas[p1]
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        var title: TextView

        constructor(itemView: View) : super(itemView) {
            title = itemView.findViewById(R.id.title) as TextView
        }

    }

}