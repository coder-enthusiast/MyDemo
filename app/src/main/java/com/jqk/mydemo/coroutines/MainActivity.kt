package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityCoroutinesMainBinding

class MainActivity : BaseActivity(){
    lateinit var binding : ActivityCoroutinesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutines_main)
        binding.view = this
    }

    fun netWork(view: View) {
        jumpActivity(NetWorkActivity().javaClass)
    }

    fun demo(view: View) {
        jumpActivity(CoroutinesActivity().javaClass)
    }

    fun switchThread(view: View) {
        jumpActivity(ThreadSwitchActivity().javaClass)
    }
}