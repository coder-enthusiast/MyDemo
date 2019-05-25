package com.jqk.mydemo.behavior

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityCustombehaviorBinding

class CustomBehaviorActivity : BaseActivity() {
    lateinit var binding: ActivityCustombehaviorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custombehavior)
        binding.view = this
    }

    fun custom1(view: View) {
        jumpActivity(CustomActivity1().javaClass)
    }
}