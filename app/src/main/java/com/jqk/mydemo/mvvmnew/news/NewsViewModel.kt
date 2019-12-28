package com.jqk.mydemo.mvvmnew.news

import androidx.lifecycle.*
import androidx.databinding.Observable
import android.util.Log
import android.view.View
import com.jqk.mydemo.mvvmnew.base.OnDataCallback
import com.jqk.commonlibrary.util.L

class NewsViewModel : ViewModel(), LifecycleObserver, Observable {
    val CONTENT: Int = 1
    val LOADING: Int = 2
    val ERROR: Int = 3

    val newsModel: NewsModel by lazy {
        NewsModel()
    }
    val news: MutableLiveData<News> by lazy {
        MutableLiveData<News>()
    }
    val changeStr: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val str: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val check: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val showDialog: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val errorStr: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val viewType: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val a: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        changeStr.value = "等待改变"
        str.value = "点我加载"
        check.value = false
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        com.jqk.commonlibrary.util.L.d("removeOnPropertyChangedCallback")
    }

    // bidning设置variable的时候调用
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        com.jqk.commonlibrary.util.L.d("addOnPropertyChangedCallback")
    }

    fun getData(boolean: Boolean) {
        com.jqk.commonlibrary.util.L.d("getData传递的参数 = " + boolean)
        showDialog.value = true
        viewType.value = LOADING
        newsModel.getNews("top", "93ff5c6fd6dc134fc69f6ffe3bc568a6", object : OnDataCallback<News> {
            override fun onSuccess(data: News) {
                showDialog.value = false
                if (data.error_code == 0) {
                    news.value = data
                    viewType.value = CONTENT
                } else {
                    errorStr.value = "获取数据失败"
                    viewType.value = ERROR
                }
            }

            override fun onError() {
                showDialog.value = false
                errorStr.value = "网络错误"
            }
        })
        newsModel.getMoreNews("top", "shehui", "guonei", "93ff5c6fd6dc134fc69f6ffe3bc568a6")
        newsModel.mapNews("top", "93ff5c6fd6dc134fc69f6ffe3bc568a6")
    }

    fun change(view: View) {
        changeStr.value = "已经改变"
        str.value = "改变改变"
        a.value = "123456"
    }

    fun cancel(view: View) {
        showDialog.value = false
        newsModel.onDestroy()
    }

    // Activity销毁时，自动调用ViewModel的onDestroy()方法
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        com.jqk.commonlibrary.util.L.d("onDestroy")
        Log.d("123", "销毁viewModel")
        newsModel.onDestroy()
    }

    // 绑定的Activity调用onDestroy时会调用该方法
    override fun onCleared() {
        com.jqk.commonlibrary.util.L.d("onCleared")
        super.onCleared()
    }
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    fun onPause() {
//        Log.d("123", "onPause")
//    }
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onStart() {
//        Log.d("123", "onStart")
//    }
}