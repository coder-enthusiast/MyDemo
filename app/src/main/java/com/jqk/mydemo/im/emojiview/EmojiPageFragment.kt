package com.jqk.mydemo.im.emojiview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jqk.mydemo.R
import com.jqk.mydemo.im.MessageUtil

class EmojiPageFragment : Fragment() {
    lateinit var contentView: View
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(R.layout.fragment_emoji_page, container, false)

        recyclerView = contentView.findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.adapter = EmojiAdapter(context!!, MessageUtil.mEmojiList)
        recyclerView.layoutManager = GridLayoutManager(context!!,4,  RecyclerView.VERTICAL, false)
        return contentView
    }
}