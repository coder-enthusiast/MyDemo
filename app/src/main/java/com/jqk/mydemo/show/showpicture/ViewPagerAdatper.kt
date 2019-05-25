package com.jqk.mydemo.show.showpicture

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup

class ViewPagerAdatper : PagerAdapter {
    var viewList: List<View>

    constructor(viewList: List<View>) : super() {
        this.viewList = viewList
    }


    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList.get(position))
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList.get(position))
        return viewList.get(position)
    }
}