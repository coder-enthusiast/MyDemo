package com.jqk.mydemo.mvvmnew.base

import io.reactivex.disposables.CompositeDisposable

open class BaseModel {
    var compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    open fun onDestroy() {

    }
}