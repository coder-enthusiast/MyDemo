package com.jqk.mydemo.kotlin.lambda

import android.view.View

class Test {
    var onCick: () -> Unit = ::onClick

    fun onClick() {

    }

    fun setOnClickListener(onClick: ()-> Unit) {
        this.onCick = onCick
    }
}