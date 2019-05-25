package com.jqk.mydemo.im.emojiview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemEmojiBinding
import com.jqk.mydemo.im.MessageUtil
import org.greenrobot.eventbus.EventBus

class EmojiAdapter : RecyclerView.Adapter<EmojiAdapter.ViewHolder> {
    val context: Context
    val datas: List<Emoji>

    constructor(context: Context, datas: List<Emoji>) : super() {
        this.context = context
        this.datas = datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemEmojiBinding>(
                LayoutInflater.from(context),
                R.layout.item_emoji, parent,
                false
        )
        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DataBindingUtil.bind<ItemEmojiBinding>(holder.itemView)?.let {
            it.emoji.setImageResource(MessageUtil.mEmojiList[position].id)
            it.emojiView.setOnClickListener {
                EventBus.getDefault().post(MessageUtil.mEmojiList[position])
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }
}