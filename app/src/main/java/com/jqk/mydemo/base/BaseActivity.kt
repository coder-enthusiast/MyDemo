package com.jqk.mydemo.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun jumpActivity(clazz: Class<*>) {
        var intent = Intent()
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}