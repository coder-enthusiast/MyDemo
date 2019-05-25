package com.jqk.mydemo.im

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemImBinding

class IMAdatper : RecyclerView.Adapter<IMAdatper.MyViewHolder> {
    var context: Context
    var datas: ArrayList<String>

    constructor(context: Context, datas: ArrayList<String>) : super() {
        this.context = context
        this.datas = datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ItemImBinding>(LayoutInflater.from(parent.context), R.layout.item_im, parent, false)
        val viewHolder = MyViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        DataBindingUtil.getBinding<ItemImBinding>(holder.itemView)?.let {
            it.message.text = MessageUtil.findEmoticon(context, datas[position])
        }
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }
}