package com.jqk.mydemo.javaCV

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityJavacvBinding

class JavaCVActivity : BaseActivity() {
    lateinit var b : ActivityJavacvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView<ActivityJavacvBinding>(this, R.layout.activity_javacv)
        b.view = this
    }

    fun recordVideo(view: View) {
        jumpActivity(RecordActivity().javaClass)
    }
}