package com.jqk.filelibrary.file

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.cartest.FileUtils
import com.jqk.filelibrary.BaseActivity
import com.jqk.filelibrary.R
import com.jqk.filelibrary.databinding.ActivityFileBinding
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FileActivity : BaseActivity() {
    lateinit var binding: ActivityFileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file)

        FileUtils.onFindFileListener = object : FileUtils.OnFindFileListener {
            override fun onFindFile(name: String) {
                Log.d("", "刷新布局 = " + name)
            }
        }

        scanFile("/storage/emulated/0", false)
    }

    fun scanFile(path: String, isRoot: Boolean) {
        Log.d("", "遍历开始")
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                // 遍历根目录
//                emitter.onNext(FileUtils.getFileByFile(path)!!)
                FileUtils.scanFiles(path, emitter)
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: String) {
                        Log.d("", "接受数据 = " + t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }
}