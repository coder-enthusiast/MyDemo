package com.jqk.mydemo.service

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.service.aidl.AidlActivity

class ServiceActivity : BaseActivity() {
    lateinit var binding: com.jqk.mydemo.databinding.ActivityServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service)
        binding.view = this
    }

    fun aidl(view: View) {
        jumpActivity(AidlActivity().javaClass)
    }
}