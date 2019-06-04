package com.jqk.mydemo.file

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cartest.CarDirectory
import com.example.cartest.DirItemAdaper
import com.example.cartest.FileUtils
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityDirBinding
import com.jqk.mydemo.file.music.MusicPlayerActivity
import com.jqk.mydemo.show.showview.BaseActivity
import com.jqk.mydemo.util.L
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DirActivity : BaseActivity() {
    lateinit var binding: ActivityDirBinding
    lateinit var fileItemAdaper: DirItemAdaper

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    Toast.makeText(this@DirActivity, "发现设备", Toast.LENGTH_SHORT).show()
                }

                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    Toast.makeText(this@DirActivity, "设备消失", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDirBinding>(this, R.layout.activity_dir)

        val intentFilter = IntentFilter()
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        registerReceiver(broadcastReceiver, intentFilter)

        scanDirectory("/", true)
    }

    fun scanDirectory(path: String, isRoot: Boolean) {
        binding.progressBar.visibility = View.VISIBLE
        Observable.create(object : ObservableOnSubscribe<CarDirectory> {
            override fun subscribe(emitter: ObservableEmitter<CarDirectory>) {
                // 遍历根目录
                emitter.onNext(FileUtils.getFileByDir(path)!!)
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CarDirectory> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: CarDirectory) {
                        setUI(t!!)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun setUI(carDirectory: CarDirectory) {

        if (carDirectory == null) {
            L.d("空")
            return
        }

        binding.progressBar.visibility = View.GONE
        fileItemAdaper = DirItemAdaper(this, carDirectory)
        binding.fileRecyclerView.adapter = fileItemAdaper
        binding.fileRecyclerView.layoutManager = LinearLayoutManager(this)

        fileItemAdaper.onClickListener = object : DirItemAdaper.OnClickListener {

            override fun onBack(path: String) {
                scanDirectory(path, false)
            }

            override fun onClick(path: String) {
                scanDirectory(path, false)
            }

            override fun onFileClick(name: String, path: String) {
                Toast.makeText(this@DirActivity, name + "-----" + path, Toast.LENGTH_SHORT).show()

                if (name.endsWith(".mp3")) {
                    val intent = Intent()
                    intent.setClass(this@DirActivity, MusicPlayerActivity()::class.java)
                    intent.putExtra("musicName", name)
                    intent.putExtra("path", path)
                    startActivity(intent)
                }
            }
        }
    }

    fun refreshUI(carDirectory: CarDirectory) {
        fileItemAdaper.data = carDirectory
        fileItemAdaper.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}