package com.jqk.mydemo.file

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityFilemodeBinding
import com.jqk.mydemo.file.dir.DirFragmentActivity
import com.jqk.mydemo.file.file.FileActivity

class FileModeActivity : BaseActivity() {
    lateinit var binding: ActivityFilemodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filemode)
        binding.view = this
    }

    fun dir(view: View) {
        jumpActivity(DirActivity().javaClass)
    }

    fun file(view: View) {
        jumpActivity(FileActivity().javaClass)
    }

    fun dirFragment(view: View) {
        jumpActivity(DirFragmentActivity().javaClass)
    }
}