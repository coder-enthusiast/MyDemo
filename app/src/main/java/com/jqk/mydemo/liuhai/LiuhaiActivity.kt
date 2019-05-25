package com.jqk.mydemo.liuhai

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityLiuhaiBinding



class LiuhaiActivity : AppCompatActivity() {
    lateinit var b: ActivityLiuhaiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_liuhai)
        b.view = this

        val layoutParams = window.attributes

    }

    fun xiaomi(view: View) {
        val intent = Intent()
        intent.setClass(this, XiaomiActivity::class.java)
        startActivity(intent)
    }
}