package com.jqk.mydemo.glide

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityGlideBinding

class GlideActivity : AppCompatActivity() {
    lateinit var b: ActivityGlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_glide)

        loadImg("https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=6aeaafb44d34970a537e187df4a3baad/a8014c086e061d95c84250a377f40ad162d9ca21.jpg"
                , b.img)

//        val requestOptions = RequestOptions()
//        Glide.with(this).load(R.drawable.icon_image).apply(requestOptions).into(b.img)
    }

    fun loadImg(url: String, imageView: ImageView) {
        GlideApp.with(this)
                .load(url)
                .circleCrop()
                .into(imageView)
    }
}
