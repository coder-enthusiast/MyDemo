package com.jqk.mydemo.liuhai

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityXiaomiBinding
import com.jqk.mydemo.util.StatusBarUtil
import android.util.Log
import android.view.Window

class XiaomiActivity : AppCompatActivity() {
    lateinit var b: ActivityXiaomiBinding

    var fullscreen: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtil.immersive(this)
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_xiaomi)
        b.view = this
        StatusBarUtil.setPadding(this, b.content)
        // 小米刘海屏适配
        if (android.os.SystemProperties.getInt("ro.miui.notch", 0) == 1) {
            val flag = 0x00000100 or 0x00000200 or 0x00000400
            try {
                val method = Window::class.java!!.getMethod("addExtraFlags",
                        Int::class.javaPrimitiveType)
                method.invoke(window, flag)
            } catch (e: Exception) {
                Log.i("123", "addExtraFlags not found.")
            }
        }
    }

    fun immersive(view: View) {
        if (!fullscreen) {
            setFullScreen(true)
            fullscreen = true
        } else {
            setFullScreen(false)
            fullscreen = false
        }
    }

    private fun setFullScreen(enable: Boolean) {
        val mDecorView = window.decorView
        if (enable) {
            val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            mDecorView.setSystemUiVisibility(uiOptions)
        } else {
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            mDecorView.setSystemUiVisibility(uiOptions)
        }
    }
}