package com.jqk.mydemo.util

import android.app.Activity
import android.util.DisplayMetrics

object ScreenUtil {
    /**
     * 获取屏幕密度
     * 单位：Float
     */
    fun getDensity(activity: Activity): Float {
        var dm = DisplayMetrics()
        dm = activity.resources.displayMetrics

        val density = dm.density
        return density
    }
    /**
     * 获取屏幕宽度
     * 单位：Int
     */
    fun getScreenWidth(activity: Activity) : Int{
        var dm = DisplayMetrics()
        dm = activity.resources.displayMetrics

        val screenWidth = dm.widthPixels

        return screenWidth
    }
    /**
     * 获取屏幕高度
     * 单位：Int
     */
    fun getScreenHeight(activity: Activity): Int {
        var dm = DisplayMetrics()
        dm = activity.resources.displayMetrics

        val screenHeight = dm.heightPixels

        return screenHeight
    }
}