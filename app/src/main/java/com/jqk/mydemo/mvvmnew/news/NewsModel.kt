package com.jqk.mydemo.mvvmnew.news

import android.util.Log
import com.jqk.mydemo.mvvmnew.base.BaseModel
import com.jqk.mydemo.mvvmnew.base.OnDataCallback
import com.jqk.mydemo.retrofitkotlin.RetrofitHttpRequest
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class NewsModel : BaseModel() {

    private var observable1: Observable<News>? = null
    private var observable2: Observable<News>? = null
    private var observable3: Observable<News>? = null
    private var observable4: Observable<News>? = null

    fun getNews(type: String, key: String, onDataCallback: OnDataCallback<News>) {

        observable1 = RetrofitHttpRequest.instance.retrofitService.getNews(type = type, key = key)
        observable1?.run {
            subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<News> {
                        override fun onComplete() {

                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }

                        override fun onNext(t: News) {
//                            Log.d("123", "返回的数据 = " + t.toString())
                            onDataCallback.onSuccess(t)
                        }

                        override fun onError(e: Throwable) {
                            onDataCallback.onError()
                        }
                    })

        }
    }

    fun getMoreNews(type1: String, type2: String, type3: String, key: String) {
//        RetrofitHttpRequest.retrofitService.getNews(type, key).con


        Observable.mergeArray(
                RetrofitHttpRequest.instance.retrofitService.getNews(type1, key),
                RetrofitHttpRequest.instance.retrofitService.getNews(type2, key),
                RetrofitHttpRequest.instance.retrofitService.getNews(type3, key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<News> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: News) {
                        Log.d("ddd", "News = " + t.toString())
                    }

                    override fun onError(e: Throwable) {

                    }
                })

    }

    fun mapNews(type: String, key: String) {

        Observable.zip(
                RetrofitHttpRequest.instance.retrofitService.getNews(type, key),
                RetrofitHttpRequest.instance.retrofitService.getNews(type, key),
                RetrofitHttpRequest.instance.retrofitService.getNews(type, key),
                object : Function3<News, News, News, DoubleNews> {
                    override fun apply(t1: News, t2: News, t3: News): DoubleNews {
                        return DoubleNews(t1, t2, t3)
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DoubleNews> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: DoubleNews) {
                        Log.d("ddd", "news1 = " + t.news1.toString())
                        Log.d("ddd", "news2 = " + t.news2.toString())
                        Log.d("ddd", "news3 = " + t.news3.toString())
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        observable1?.run { unsubscribeOn(Schedulers.io()) }
        compositeDisposable.clear()
    }
}