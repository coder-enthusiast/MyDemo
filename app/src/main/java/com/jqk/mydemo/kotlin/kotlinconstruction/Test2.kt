package com.jqk.mydemo.kotlin.kotlinconstruction

import com.jqk.commonlibrary.util.L

class Test2 {

    var sum: ((x: Int, y: Int) -> Int) = { x, y -> x + y }

    var listener: Listener? = null

    interface Listener {
        fun onClick(i: Int)
        fun onLongClick(i: Int)
    }

    fun abc() {
        sum?.invoke(1, 2)
        L.d("sum 返回值 = " + sum(1, 2))
        sum(1, 2)

        listener?.onClick(10)
        listener?.onLongClick(11)

    }

    fun setaSum(sum: (Int, Int) -> Int) {
        this.sum = sum
    }
}