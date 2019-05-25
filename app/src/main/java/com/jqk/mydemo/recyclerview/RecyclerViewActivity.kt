package com.jqk.mydemo.recyclerview

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityRecyclerviewBinding
import com.jqk.mydemo.recyclerview.nesting.NestingActivity

class RecyclerViewActivity : BaseActivity() {
    lateinit var binding: ActivityRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview)
        binding.view = this

    }

    fun editText(view: View) {
        jumpActivity(RecyclerViewAndEditTextActivity().javaClass)
    }

    fun nesting(view: View) {
        jumpActivity(NestingActivity().javaClass)
    }
}