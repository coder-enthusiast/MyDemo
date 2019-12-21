package com.jqk.filelibrary.dir

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.filelibrary.R
import com.jqk.filelibrary.databinding.ItemDirBinding

class DirItemAdaper : RecyclerView.Adapter<DirItemAdaper.ViewHolder> {
    val context: Context
    var data: CarDirectory

    lateinit var onClickListener: OnClickListener

    constructor(context: Context, data: CarDirectory) : super() {
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DirItemAdaper.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemDirBinding>(LayoutInflater.from(context), R.layout.item_dir, p0, false)
        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        if (data.root == true) {
            return data.directorys.size + data.files.size
        } else {
            return data.directorys.size + data.files.size + 1
        }
    }

    override fun onBindViewHolder(p0: DirItemAdaper.ViewHolder, p1: Int) {

        DataBindingUtil.getBinding<ItemDirBinding>(p0.itemView)?.let {
            if (data.root == true) {
                if (p1 <= (data.directorys.size - 1)) {
                    it.img.setImageResource(R.drawable.icon_dir)
                    it.name.text = data.directorys[p1].name

                    it.itemView.setOnClickListener {
                        Log.d("", data.directorys[p1].name)
                        onClickListener.onClick(data.directorys[p1].path)
                    }
                } else {
                    it.img.setImageResource(R.drawable.icon_file)
                    it.name.text = data.files[p1 - data.directorys.size].name

                    it.itemView.setOnClickListener {
                        onClickListener.onFileClick(data.files[p1 - data.directorys.size].name, data.files[p1 - 1 - data.directorys.size].path)
                    }
                }
            } else {
                if (p1 == 0) {
                    it.img.setImageResource(R.drawable.icon_dir)
                    it.name.text = ".."
                    it.itemView.setOnClickListener {
                        onClickListener.onBack(data.dirPath)
                    }

                } else if (p1 > 0 && p1 - 1 <= (data.directorys.size - 1)) {
                    it.img.setImageResource(R.drawable.icon_dir)
                    it.name.text = data.directorys[p1 - 1].name

                    it.itemView.setOnClickListener {
                        Log.d("", data.directorys[p1 - 1].dirName)
                        onClickListener.onClick(data.directorys[p1 - 1].path)
                    }
                } else {
                    it.img.setImageResource(R.drawable.icon_file)
                    it.name.text = data.files[p1 - 1 - data.directorys.size].name
                    it.itemView.setOnClickListener {
                        onClickListener.onFileClick(data.files[p1 - 1 - data.directorys.size].name, data.files[p1 - 1 - data.directorys.size].path)
                    }
                }
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    interface OnClickListener {
        fun onBack(path: String)
        fun onClick(path: String)
        fun onFileClick(name: String, path: String)
    }
}