package com.jqk.mydemo.jetpack.paging.bypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityBypageBinding

/**
 * Created by jiqingke
 * on 2019/2/15
 */
class ByPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityBypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bypage)

        val adapter = ByPageAdatper()

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