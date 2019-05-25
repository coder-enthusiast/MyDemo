package com.jqk.mydemo.jetpack.workmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityWorkmanagerBinding
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by jiqingke
 * on 2019/2/20
 */
class WorkManagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkmanagerBinding

    lateinit var timingRequest: PeriodicWorkRequest.Builder
    lateinit var photoCheckWork: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workmanager)
        binding.view = this

        timingRequest =
                PeriodicWorkRequestBuilder<PhotoCheckWorker>(2000, TimeUnit.MILLISECONDS)
        photoCheckWork = timingRequest.build()
    }

    fun start(view: View) {
        val compressionWork = OneTimeWorkRequestBuilder<CompressWorker>()
                .setInputData(Data.Builder().putInt("id", 1).build())
                .build()
        WorkManager.getInstance().enqueue(compressionWork)
        WorkManager.getInstance().getWorkInfoByIdLiveData(compressionWork.id)
                .observe(this, Observer { workInfo ->
                    // Do something with the status
                    if (workInfo != null && workInfo.state.isFinished) {
                        Log.d("jiqingke", "workInfo = " + workInfo.outputData.getLong("data", 0))
                    }
                })
    }

    fun timing(view: View) {
        WorkManager.getInstance().enqueue(photoCheckWork)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 根据ID取消任务
        val compressionWorkId: UUID = photoCheckWork.getId()
        WorkManager.getInstance().cancelWorkById(compressionWorkId)
    }
}