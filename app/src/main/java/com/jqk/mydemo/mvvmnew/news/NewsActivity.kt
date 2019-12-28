package com.jqk.mydemo.mvvmnew.news

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityNewsBinding
import com.jqk.mydemo.mvvmnew.base.BaseView

class NewsActivity : BaseView() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel

    val CONTENT: Int = 1
    val LOADING: Int = 2
    val ERROR: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        newsViewModel = ViewModelProviders.of(this).get<NewsViewModel>(NewsViewModel::class.java)
        // 将binding中的livedata跟LifecycleOwner关联起来
        binding.setLifecycleOwner(this)
        binding.viewModel = newsViewModel

        // 添加viewModel对Activity生命周期的观察
        getLifecycle().addObserver(newsViewModel)

        newsViewModel.check.observe(this, Observer {
            Toast.makeText(this, "" + it, Toast.LENGTH_SHORT).show()
        })

        // 创造liveData的观察者，可以写成内部类
        // 绑定livedata跟LifecycleOwner并监听livedata的改变
        newsViewModel.news.observe(this, object : Observer<News> {
            override fun onChanged(t: News?) {
                setData(t!!)
            }
        })
        newsViewModel.showDialog.observe(this, Observer {
            if (it!!) {
                showProgress()
            } else {
                hideProgress()
            }
        })
        newsViewModel.errorStr.observe(this, Observer {
            Toast.makeText(this, it!!, Toast.LENGTH_SHORT).show()
        })
        newsViewModel.viewType.observe(this, Observer {
            showVeiw(it!!)
        })

        // 通过databinding赋值降viewModel中的livedata与布局关联起来
        binding.a = newsViewModel.a

        newsViewModel.a.observe(this, Observer {
            Toast.makeText(this, it!!, Toast.LENGTH_SHORT).show()
        })
    }

    fun showVeiw(viewType: Int) {
        binding.contentView.visibility = View.GONE
        binding.loadingView.visibility = View.GONE
        binding.errorView.visibility = View.GONE
        when (viewType) {
            CONTENT -> {
                binding.contentView.visibility = View.VISIBLE
            }
            LOADING -> {
                binding.loadingView.visibility = View.VISIBLE
            }
            ERROR -> {
                binding.errorView.visibility = View.VISIBLE
            }
        }
    }

    fun setData(datas: News) {
        var adapter = NewsAdapter(this, datas.result.data)
        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
    }
}