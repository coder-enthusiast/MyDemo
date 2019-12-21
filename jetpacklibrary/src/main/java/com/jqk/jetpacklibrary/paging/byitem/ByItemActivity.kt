package com.jqk.jetpacklibrary.paging.byitem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.jqk.jetpacklibrary.R
import com.jqk.jetpacklibrary.databinding.ActivityByitemBinding

/**
 * Created by jiqingke
 * on 2019/2/17
 */
class ByItemActivity : AppCompatActivity() {
    lateinit var binding: ActivityByitemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_byitem)

        val adapter = ByItemAdatper()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val data = LivePagedListBuilder(MyDataSourceFactory(), PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()).build()

        data.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}