package com.jqk.mydemo.mvvmnew

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityNewmvvmBinding
import com.jqk.mydemo.mvvmnew.news.NewsActivity

class NewMVVMActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewmvvmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newmvvm)
        binding.view = this
    }

    fun jumpNews(view: View) {
        var intent = Intent()
        intent.setClass(this, NewsActivity::class.java)
        startActivity(intent)
    }
}