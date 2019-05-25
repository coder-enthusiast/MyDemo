package com.jqk.mydemo.im.emojiview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class EmojiViewPagerAdapter : FragmentPagerAdapter {
    val fragments: ArrayList<Fragment>

    constructor(fm: FragmentManager?, fragments: ArrayList<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}