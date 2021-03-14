package com.jqk.mydemo.mvvmnew.news

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemNewsBinding
import com.jqk.mydemo.glide.GlideApp

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    var onCick: () -> Unit = ::onClick

    fun onClick() {

    }

    fun setOnClickListener(onClick: ()-> Unit) {
        this.onCick = onCick
    }

    var context: Context
    var datas: List<News.Result.Data>

    constructor(context: Context, datas: List<News.Result.Data>) : super() {
        this.context = context
        this.datas = datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemNewsBinding>(LayoutInflater.from(parent.context), R.layout.item_news, parent, false)
        var viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        DataBindingUtil.getBinding<ItemNewsBinding>(holder.itemView)?.run {
            news = datas[position]

            img.setOnClickListener {
                onClick()
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View?) : super(itemView!!)
    }

    @BindingAdapter(value = *arrayOf("imageUrl"))
    fun setImageUrl(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context).load(url).into(imageView)
    }
}