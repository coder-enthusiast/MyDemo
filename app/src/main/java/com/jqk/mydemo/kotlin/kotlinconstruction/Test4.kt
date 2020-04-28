package com.jqk.mydemo.kotlin.kotlinconstruction

import com.jqk.commonlibrary.util.L

class Test4 {
    interface Callbacks {

        fun onCurrentLocation(location: String)

        fun onError()
    }

    class CallbacksImpl(private val onCurrentLocationF: (String) -> Unit, private val onErrorF: () -> Unit) : Callbacks {
        override fun onCurrentLocation(location: String) {
            onCurrentLocationF(location)
        }

        override fun onError() {
            onErrorF()
        }
    }

    lateinit var mCallBack: CallbacksImpl
    fun setListener(callBack: CallbacksImpl) {
        L.d("-------->设置接口回调")
        mCallBack = callBack
    }

    fun abc() {
        mCallBack.onCurrentLocation("haha")
        mCallBack.onError()
    }
}