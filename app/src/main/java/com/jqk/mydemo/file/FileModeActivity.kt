package com.jqk.mydemo.file

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.databinding.ActivityFilemodeBinding
import com.jqk.mydemo.file.dir.DirFragmentActivity
import com.jqk.mydemo.file.file.FileActivity
import com.jqk.mydemo.util.L
import java.io.File

class FileModeActivity : BaseActivity() {
    lateinit var binding: ActivityFilemodeBinding

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                Intent.ACTION_MEDIA_MOUNTED -> {
                    L.d("监听到了")
                }
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    L.d("插入")

                    val file = File("/storage/usb0/")
                    L.d("" + file.exists())
                    L.d("" + file.canRead())
                    L.d("" + file.canWrite())

                    Thread(Runnable{
                        while(!file.canRead()) {
                        }

                        for (file in file.listFiles()) {
                            L.d("filelist = " + file.toString())
                        }
                    }).start()

                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    L.d("拔出")
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