package com.jqk.mydemo.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jqk.mydemo.R

class FirstFragment : LazyLoadFragment() {
    override fun setContentView(): Int {
        return R.layout.fragment_first
    }

    override fun initView() {

    }

    override fun lazyLoad() {

    }
}