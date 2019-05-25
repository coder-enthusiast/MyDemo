package com.jqk.mydemo.jetpack.paging.bypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemPagingBinding
import com.jqk.mydemo.jetpack.paging.Bean

/**
 * Created by jiqingke
 * on 2019/2/15
 */
class ByPageAdatper : PagedListAdapter<Bean, ByPageAdatper.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPagingBinding>(LayoutInflater.from(parent.context), R.layout.item_paging, parent, false)
        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DataBindingUtil.bind<ItemPagingBinding>(holder.itemView)?.let {
            it.name.text = (getItem(position) as Bean).name
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bean>() {
            override fun areItemsTheSame(oldItem: Bean, newItem: Bean): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Bean, newItem: Bean): Boolean {
                return oldItem == newItem
            }
        }
    }
}