package com.jqk.mydemo.show.showpicture

import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityShowpicturetestBinding
import com.jqk.mydemo.glide.GlideApp
import io.reactivex.Observable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class ShowPictureTestActivity : AppCompatActivity() {
    lateinit var b: ActivityShowpicturetestBinding

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var imgPathList: MutableList<String>
    var bitmapList: MutableList<Bitmap>

    init {
        imgPathList = mutableListOf()
        bitmapList = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_showpicturetest)

        val dm = resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels

        imgPathList?.run {
            add("https://file.izuiyou.com/img/view/id/501047609")
            add("https://file.izuiyou.com/img/view/id/500848543")
            add("https://file.izuiyou.com/img/view/id/503798117")
            add("https://file.izuiyou.com/img/view/id/501047609")
            add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545738381474&di=e0a11f9d203d03308fdeac66d74afa1d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F10%2F20150110202052_nwCkn.thumb.700_0.jpeg")
            add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546060912330&di=8c77fe191a0b845090f04b3a37be7ff2&imgtype=0&src=http%3A%2F%2Fbbsfiles.vivo.com.cn%2Fvivobbs%2Fattachment%2Fforum%2F201707%2F27%2F120851c3lugr4lbumfxz04.jpg")
        }

        var i = 0

        Observable.create<Bitmap> {

            for (i in 0..imgPathList.size - 1) {
                GlideApp
                        .with(this)
                        .asBitmap()
                        .load(imgPathList[i])
                        .into(object : SimpleTarget<Bitmap>(200, 400) {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                val width = resource.width
                                val height = resource.height
                                val n = screenWidth * 1.0f / width * 1.0f * height / screenWidth
                                if (n > 1.5) {
                                    // 截取长图的一部分，宽高跟规定的宽高相等
                                    val baos = ByteArrayOutputStream()
                                    resource.compress(Bitmap.CompressFormat.JPEG, 1, baos)
                                    val inputStream = ByteArrayInputStream(baos.toByteArray())
                                    var decoder: BitmapRegionDecoder? = null
                                    try {
                                        decoder = BitmapRegionDecoder.newInstance(inputStream, true)
                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    }

                                    val opts = BitmapFactory.Options()

                                    val rect = Rect()
                                    rect.set(0, 0, 200, (200 * (screenHeight * 1.0 / screenWidth * 1.0)).toInt())
                                    val bt = decoder!!.decodeRegion(rect, opts)

                                    Log.d("123", "处理的bitmap = " + bt.width)
                                    Log.d("123", "处理的bitmap = " + bt.height)
                                    Log.d("123", "处理的bitmap = " + bt.toString())
                                    Log.d("123", "----------------------------")
                                    it.onNext(bt)
                                } else {
                                    it.onNext(resource)
                                }
                            }

                            override fun onLoadFailed(errorDrawable: Drawable?) {
                                Log.d("123", "加载图片失败")
                                super.onLoadFailed(errorDrawable)
                            }
                        })
            }

        }.subscribe {
            i++
            when (i) {
                1 -> {
                    b.img1.setImageBitmap(it)
                }
                2 -> {
                    b.img2.setImageBitmap(it)
                }
                3 -> {
                    b.img3.setImageBitmap(it)
                }
                4 -> {
                    b.img4.setImageBitmap(it)
                }
                5 -> {
                    b.img5.setImageBitmap(it)
                }
                6 -> {
                    b.img6.setImageBitmap(it)
                }
            }
        }
    }
}