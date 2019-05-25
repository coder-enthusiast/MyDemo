package com.jqk.mydemo.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by jiqingke
 * on 2019/2/20
 */
class PhotoCheckWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("jiqingke", "我是定时任务")
        return Result.success()
    }
}