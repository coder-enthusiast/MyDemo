package com.jqk.mydemo

import android.Manifest
import android.os.Bundle
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import android.view.View
import com.jqk.mydemo.base.BaseActivity
import com.jqk.mydemo.behavior.CustomBehaviorActivity
import com.jqk.mydemo.coroutines.CoroutinesActivity
import com.jqk.mydemo.coroutines.MainActivity
import com.jqk.mydemo.dagger2.DaggerActivity
import com.jqk.mydemo.databinding.ActivityMainBinding
import com.jqk.mydemo.file.FileModeActivity
import com.jqk.mydemo.glide.GlideActivity
import com.jqk.mydemo.im.IMActivity
import com.jqk.mydemo.liuhai.LiuhaiActivity
import com.jqk.mydemo.mvvmnew.NewMVVMActivity
import com.jqk.mydemo.okhttp.OkhttpActivity
import com.jqk.mydemo.recyclerview.RecyclerViewActivity
import com.jqk.mydemo.retrofitkotlin.RetrofitActivity
import com.jqk.mydemo.rx.RxTestActivity
import com.jqk.mydemo.service.ServiceActivity
import com.jqk.mydemo.show.showpicture.ShowPictureActivity
import com.jqk.mydemo.webview.WebViewActivityForJava
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : BaseActivity() {

    lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        b.view = this

        requestAllPower()
    }

    fun service(view: View) {
        jumpActivity(ServiceActivity().javaClass)
    }

    fun showPicture(view: View) {
        jumpActivity(ShowPictureActivity().javaClass)
    }

    fun mvvm(view: View) {
        jumpActivity(NewMVVMActivity().javaClass)
    }

    fun webView(view: View) {
        jumpActivity(WebViewActivityForJava().javaClass)
    }

    fun rxJava(view: View) {
        jumpActivity(RxTestActivity().javaClass)
    }

    fun okHttp(view: View) {
        jumpActivity(OkhttpActivity().javaClass)
    }

    fun retrofit(view: View) {
        jumpActivity(RetrofitActivity().javaClass)
    }

    fun dagger(view: View) {
        jumpActivity(DaggerActivity().javaClass)
    }

    fun liuhai(view: View) {
        jumpActivity(LiuhaiActivity().javaClass)
    }

    fun glide(view: View) {
        jumpActivity(GlideActivity().javaClass)
    }

    fun recyclerView(view: View) {
        jumpActivity(RecyclerViewActivity().javaClass)
    }

    fun jetpack(view: View) {
        jumpActivity(JetPackActivity().javaClass)
    }

    fun file(view: View) {
        jumpActivity(FileModeActivity().javaClass)
    }

    fun customBehavior(view: View) {
        jumpActivity(CustomBehaviorActivity().javaClass)
    }

    fun im(view: View) {
        jumpActivity(IMActivity().javaClass)
    }

    fun coroutines(view: View) {
        jumpActivity(MainActivity().javaClass)
    }

    fun requestAllPower() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) {
                        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
