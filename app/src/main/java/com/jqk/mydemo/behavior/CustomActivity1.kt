package com.jqk.mydemo.behavior

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityCustombehavior1Binding

class CustomActivity1 : BaseActivity() {
    lateinit var binding: ActivityCustombehavior1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custombehavior1)

        val datas = arrayListOf<String>()
        for (i in 0..20) {
            datas.add("第" + i + "行数据")
        }

        binding.myList.adapter = BehaviorAdapter(this, datas)
        binding.myList.layoutManager = LinearLayoutManager(this)

    }
}