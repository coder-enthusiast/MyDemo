package com.jqk.filelibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.filelibrary.databinding.ActivityFilemodeBinding
import com.jqk.filelibrary.dir.DirActivity
import com.jqk.filelibrary.dir.DirFragmentActivity
import com.jqk.filelibrary.file.FileActivity
import java.io.File

class FileModeActivity : BaseActivity() {
    lateinit var binding: ActivityFilemodeBinding

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                Intent.ACTION_MEDIA_MOUNTED -> {
                    Log.d("", "监听到了")
                }
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    Log.d("", "插入")

                    val file = File("/storage/usb0/")
                    Log.d("", "" + file.exists())
                    Log.d("", "" + file.canRead())
                    Log.d("", "" + file.canWrite())

                    Thread(Runnable{
                        while(!file.canRead()) {
                        }

                        for (file in file.listFiles()) {
                            Log.d("", "filelist = " + file.toString())
                        }
                    }).start()

                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    Log.d("", "拔出")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filemode)
        binding.view = this

        val intentFilter = IntentFilter()

        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)

        registerReceiver(broadcastReceiver, intentFilter)
    }

    fun dir(view: View) {
        jumpActivity(DirActivity().javaClass)
    }

    fun file(view: View) {
        jumpActivity(FileActivity().javaClass)
    }

    fun dirFragment(view: View) {
        jumpActivity(DirFragmentActivity().javaClass)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}