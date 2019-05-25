package com.jqk.mydemo.util

import android.content.Context
import android.widget.ImageView
import com.jqk.mydemo.glide.GlideApp

object GlideUtil {
    fun loadImg(context: Context, path: String, view: ImageView) {
        GlideApp
                .with(context)
                .load(path)
                .into(view)
    }
}