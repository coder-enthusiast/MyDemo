package com.jqk.loadmorelibrary

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jqk.loadmorelibrary.databinding.ItemFooterBinding

/**
 * Created by Administrator on 2017/11/16 0016.
 */
class LoadmoreAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    private val VIEWTYPE_HEADER: Int = 0
    private val VIEWTYPE_FOOTER: Int = 1
    private val VIEWTYPE_NORMAL: Int = 2

    private var mAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>
    private var mRecylcerView: LRecyclerView
    private var itemFooterBinding: ItemFooterBinding? = null
    private var loadmoreFinish: Boolean = false
    private var hasHeaderView: Boolean = false
    private var headerView: View? = null

    constructor(mAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>, mRecylcerView: LRecyclerView) {
        this.mAdapter = mAdapter
        this.mRecylcerView = mRecylcerView

        if (mRecylcerView.layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
            (mRecylcerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager).spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    val type = getItemViewType(position)

                    if (type == VIEWTYPE_FOOTER || type == VIEWTYPE_HEADER) {
                        return (mRecylcerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager).getSpanCount()
                    } else {
                        return 1
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (hasHeaderView) {
            return mAdapter.itemCount + 2
        } else {
            return mAdapter.itemCount + 1
        }

    }

    override fun getItemViewType(position: Int): Int {

        if (hasHeaderView) {
            if (position == 0) {
                return VIEWTYPE_HEADER
            } else if (position == mAdapter.itemCount + 1) {
                return VIEWTYPE_FOOTER
            } else {
                return VIEWTYPE_NORMAL
            }
        } else {
            if (position < mAdapter.itemCount) {
                return VIEWTYPE_NORMAL
            } else {
                return VIEWTYPE_FOOTER
            }
        }
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        if (hasHeaderView) {
            if (holder is FooterViewHolder) {
                if (loadmoreFinish) {
                    nomore()
                } else {
                    progress()
                }
            } else if (holder is HeaderViewHolder) {

            } else {
                mAdapter.onBindViewHolder(holder, position - 1)
            }
        } else {
            if (holder is FooterViewHolder) {
                if (loadmoreFinish) {
                    nomore()
                } else {
                    progress()
                }
            } else {
                mAdapter.onBindViewHolder(holder, position)
            }
        }

        itemFooterBinding?.failView?.setOnClickListener {
            progress()
            mRecylcerView.onLoadMoreListener?.onLoadmore()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        if (hasHeaderView) {
            if (viewType == VIEWTYPE_FOOTER) {
                val itemFooterBinding = DataBindingUtil.inflate<ItemFooterBinding>(LayoutInflater.from(parent!!.context), R.layout.item_footer, parent, false)
                this.itemFooterBinding = itemFooterBinding
                return FooterViewHolder(itemFooterBinding)
            } else if (viewType == VIEWTYPE_HEADER) {
                return HeaderViewHolder(headerView!!)
            } else {
                return mAdapter.onCreateViewHolder(parent, viewType)
            }
        } else {
            if (viewType == VIEWTYPE_FOOTER) {
                val itemFooterBinding = DataBindingUtil.inflate<ItemFooterBinding>(LayoutInflater.from(parent!!.context), R.layout.item_footer, parent, false)
                this.itemFooterBinding = itemFooterBinding
                return FooterViewHolder(itemFooterBinding)
            } else {
                return mAdapter.onCreateViewHolder(parent, viewType)
            }
        }
    }

    fun progress() {
        loadmoreFinish = false
        mRecylcerView.loadmoreFinish = false
        itemFooterBinding?.footView?.visibility = View.VISIBLE
        itemFooterBinding?.progressView?.visibility = View.VISIBLE
        itemFooterBinding?.nomoreView?.visibility = View.INVISIBLE
        itemFooterBinding?.failView?.visibility = View.INVISIBLE
    }

    fun nomore() {
        loadmoreFinish = true
        mRecylcerView.loadmoreFinish = true
        itemFooterBinding?.footView?.visibility = View.VISIBLE
        itemFooterBinding?.progressView?.visibility = View.INVISIBLE
        itemFooterBinding?.nomoreView?.visibility = View.VISIBLE
        itemFooterBinding?.failView?.visibility = View.INVISIBLE
    }

    fun success() {
        loadmoreFinish = false
        mRecylcerView.loadmoreFinish = false
        itemFooterBinding?.footView?.visibility = View.VISIBLE
        itemFooterBinding?.progressView?.visibility = View.INVISIBLE
        itemFooterBinding?.nomoreView?.visibility = View.INVISIBLE
        itemFooterBinding?.failView?.visibility = View.INVISIBLE
    }

    fun fail() {
        loadmoreFinish = false
        mRecylcerView.loadmoreFinish = false
        itemFooterBinding?.footView?.visibility = View.VISIBLE
        itemFooterBinding?.failView?.visibility = View.VISIBLE
        itemFooterBinding?.progressView?.visibility = View.INVISIBLE
        itemFooterBinding?.nomoreView?.visibility = View.INVISIBLE
    }

    fun reset() {
        loadmoreFinish = false
        mRecylcerView.loadmoreFinish = false
        itemFooterBinding?.footView?.visibility = View.GONE
    }

    fun addHeaderView(headerView: View) {
        this.headerView = headerView
        hasHeaderView = true
    }

    inner class FooterViewHolder(itemFooterBinding: ItemFooterBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemFooterBinding.root) {

    }

    inner class HeaderViewHolder(headerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(headerView) {

    }
}