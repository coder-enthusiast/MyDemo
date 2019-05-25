package com.jqk.mydemo.mvvmnew.base

interface OnDataCallback<T> {
    fun onSuccess(data: T)

    fun onError()
}