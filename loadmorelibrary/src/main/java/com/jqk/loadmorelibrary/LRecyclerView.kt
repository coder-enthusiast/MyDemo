package com.jqk.loadmorelibrary

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by Administrator on 2017/11/16 0016.
 */

// 如果item数量较少需手动调用setNomoreData()
open class LRecyclerView : androidx.recyclerview.widget.RecyclerView {
    var onLoadMoreListener: OnLoadmoreListener? = null
    var loadmoreAdapter: LoadmoreAdapter? = null

    var loadmoreFinish: Boolean = false

    var firstLoadData: Boolean = true

    fun setOnLoadmoreListener(onLoadmoreListener: OnLoadmoreListener) {
        this.onLoadMoreListener = onLoadmoreListener
    }

    interface OnLoadmoreListener {
        fun onLoadmore()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        onScroll()
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        loadmoreAdapter = LoadmoreAdapter(adapter!!, this)
        super.setAdapter(loadmoreAdapter)
    }

    /**
     * 加载更多成功
     */
    fun loadmoreSuccess() {
        notifyDataSetChanged()
        loadmoreFinish = false
        loadmoreAdapter!!.success()
    }

    /**
     * 加载更多失败
     */
    fun loadmoreFail() {
        loadmoreFinish = false
        loadmoreAdapter!!.fail()
    }

    /**
     * 没有更多数据
     */
    fun setNomoreData() {
        loadmoreFinish = true
        loadmoreAdapter!!.nomore()
    }

    fun notifyDataSetChanged() {
        loadmoreAdapter!!.notifyDataSetChanged()
    }

    fun addHeadView(view: View) {
        loadmoreAdapter?.addHeaderView(view) ?: throw Exception("先设置Adapter再添加头布局")
    }

    fun reset() {
        firstLoadData = true
        loadmoreFinish = false
        loadmoreAdapter!!.reset()
    }

    fun onScroll() {
        this.setOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {

                if (!canScrollVertically(1)) {

                    if (!loadmoreFinish) {
                        onLoadMoreListener?.onLoadmore()
                        loadmoreFinish = true
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
}