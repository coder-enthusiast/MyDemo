package com.jqk.mydemo.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityFragmentBinding
import com.jqk.mydemo.fragment.normal.NormalFragmentActivity
import com.jqk.mydemo.fragment.viewpager.ViewpagerFragmentActivity

class FragmentActivity : BaseActivity() {
   lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment)
        binding.view = this
    }

    fun normal(v: View) {
        jumpActivity(NormalFragmentActivity::class.java)
    }

    fun viewPager(v: View) {
        jumpActivity(ViewpagerFragmentActivity::class.java)
    }
}