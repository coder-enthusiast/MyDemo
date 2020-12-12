package com.jqk.mydemo.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jqk.mydemo.R

class SecondFragment : LazyLoadFragment() {

    override fun setContentView(): Int {
        return R.layout.fragment_second
    }

    override fun initView() {

    }

    override fun lazyLoad() {

    }
}