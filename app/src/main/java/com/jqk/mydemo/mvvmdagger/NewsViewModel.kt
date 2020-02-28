package com.jqk.mydemo.mvvmdagger

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jqk.commonlibrary.util.L
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var newsRetroitService: NewsRetroitService

    init {
        L.d("初始化NewsViewModel")
    }

    fun getData() {
        L.d("getdata")
        newsRetroitService.getNews("top", "93ff5c6fd6dc134fc69f6ffe3bc568a6")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<News> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: News) {
                        L.d(t.toString())
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }
}