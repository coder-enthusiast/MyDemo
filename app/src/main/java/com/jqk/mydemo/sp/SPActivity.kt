package com.jqk.mydemo.sp

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivitySpBinding

class SPActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sp)

        var user = User()
        user.name = "jiqngke"
        user.age = 27
        var book = User.Book()
        book.bookName = "hahaha"
        user.book = book

        SPUtils.putBean(this, "user", user)

        binding.message.text = (SPUtils.getBean(this, "user") as User).toString()
    }
}