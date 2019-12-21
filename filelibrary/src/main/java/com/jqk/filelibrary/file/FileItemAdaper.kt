package com.example.cartest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.filelibrary.R
import com.jqk.filelibrary.databinding.ItemFileBinding

class FileItemAdaper : RecyclerView.Adapter<FileItemAdaper.ViewHolder> {
    val context: Context
    var data: ArrayList<String>

    lateinit var onClickListener: OnClickListener

    constructor(context: Context, data: ArrayList<String>) : super() {
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FileItemAdaper.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemFileBinding>(LayoutInflater.from(context), R.layout.item_file, p0, false)
        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: FileItemAdaper.ViewHolder, p1: Int) {

        DataBindingUtil.getBinding<ItemFileBinding>(p0.itemView)?.let {
            it.name.text = data[p1]
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    interface OnClickListener {
        fun onBack(path: String)
        fun onClick(path: String)
        fun onFileClick(name: String)
    }
}