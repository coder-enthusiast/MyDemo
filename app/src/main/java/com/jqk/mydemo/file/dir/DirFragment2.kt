package com.jqk.mydemo.file.dir

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cartest.CarDirectory
import com.example.cartest.DirItemAdaper
import com.example.cartest.FileUtils
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.FragmentDir2Binding
import com.jqk.mydemo.databinding.FragmentDirBinding
import com.jqk.mydemo.util.L
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DirFragment2 : Fragment() {
    lateinit var binding: FragmentDir2Binding
    lateinit var fileItemAdaper: DirItemAdaper

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    Toast.makeText(context, "发现设备", Toast.LENGTH_SHORT).show()
                }

                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    Toast.makeText(context, "设备消失", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentDir2Binding>(LayoutInflater.from(context), R.layout.fragment_dir2, container, false)

        val intentFilter = IntentFilter()
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        context!!.registerReceiver(broadcastReceiver, intentFilter)

        scanDirectory("/", true)

        return binding.root
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
        fileItemAdaper = DirItemAdaper(context!!, carDirectory)
        binding.fileRecyclerView.adapter = fileItemAdaper
        binding.fileRecyclerView.layoutManager = LinearLayoutManager(context)

        fileItemAdaper.onClickListener = object : DirItemAdaper.OnClickListener {

            override fun onBack(path: String) {
                scanDirectory(path, false)
            }

            override fun onClick(path: String) {
                scanDirectory(path, false)
            }

            override fun onFileClick(name: String, path: String) {
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun refreshUI(carDirectory: CarDirectory) {
        fileItemAdaper.data = carDirectory
        fileItemAdaper.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(broadcastReceiver)
    }
}