package com.jqk.mydemo.recyclerview.nesting

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityNestingBinding

// 嵌套滚动
class NestingActivity : BaseActivity() {
    lateinit var binding: ActivityNestingBinding
    lateinit var datas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nesting)

        datas = createDatas()

        Log.d("jiqingke", "datas = " + datas.toString())

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(this, datas)
    }

    fun createDatas(): ArrayList<String> {
        var datas = arrayListOf<String>()
        for (i in 0..20) {
            datas.add(i.toString())
        }

        return datas
    }
}