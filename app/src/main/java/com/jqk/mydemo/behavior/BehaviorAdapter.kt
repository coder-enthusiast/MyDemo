package com.jqk.mydemo.behavior

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemBehaviorBinding

class BehaviorAdapter : RecyclerView.Adapter<BehaviorAdapter.MyViewHolder> {
    val context: Context;
    val datas: ArrayList<String>

    constructor(context: Context, datas: ArrayList<String>) : super() {
        this.context = context
        this.datas = datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ItemBehaviorBinding>(LayoutInflater.from(parent.context), R.layout.item_behavior, parent, false)
        val viewHolder = MyViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        DataBindingUtil.bind<ItemBehaviorBinding>(holder.itemView)?.let {
            it.title.text = datas[position]
        }
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }
}