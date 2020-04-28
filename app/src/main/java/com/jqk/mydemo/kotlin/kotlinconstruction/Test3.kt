package com.jqk.mydemo.kotlin.kotlinconstruction

typealias Fun1 = (String) -> Unit
typealias Fun2 = (String, String) -> Unit
typealias Fun3 = (Fun1) -> Unit
typealias Fun4 = (Fun2) -> Unit
typealias CallBack = (Fun3, Fun4) -> Unit

class Test3 {
    lateinit var newCall: CallBack

    //用于实现接口回调的方法
    fun callBack(call: CallBack) {
        newCall = call
        println("callBack调用")
    }

    var mFun:Fun1? = null

    fun abc() {
        val fun1:Fun3 = {
            mFun = it
        }
        val fun2:Fun4 = {
            it("2","3")
        }

        newCall(fun1,fun2)
        mFun?.invoke("1")
    }
}