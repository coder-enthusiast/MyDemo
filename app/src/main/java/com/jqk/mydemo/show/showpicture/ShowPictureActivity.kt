package com.jqk.mydemo.show.showpicture

import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityShowpictureBinding
import com.jqk.mydemo.databinding.LayoutViewpagerBinding
import com.jqk.mydemo.glide.GlideApp
import com.jqk.mydemo.util.Constants
import com.jqk.commonlibrary.util.StatusBarUtil
import io.reactivex.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * 在Activity最上方添加一个viewpager，先将图片处理（具体如何操作看需求来定），区分长图跟正常图片，图片展开用属性动画完成（制造视觉假象）
 */
class ShowPictureActivity : AppCompatActivity(), RecyclerViewAdatper.OnItemClickListener, PictureView.OnHideImgListener {
    lateinit var b: ActivityShowpictureBinding
    lateinit var vpb: LayoutViewpagerBinding
    var imgPathList: MutableList<String>
    var viewList: MutableList<PageView>
    var typeList: MutableList<ViewType>
    lateinit var viewPagerAdatper: ViewPagerAdatper
    lateinit var recyclerViewAdatper: RecyclerViewAdatper

    lateinit var bgView: View
    val pictureView by lazy {
        PictureView(this@ShowPictureActivity)
    }
    lateinit var viewPager: ViewPager

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var density: Float = 0f

    var firstClick = false

    init {
        imgPathList = mutableListOf()
        viewList = mutableListOf()
        typeList = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.jqk.commonlibrary.util.StatusBarUtil.immersive(this)
        b = DataBindingUtil.setContentView(this, R.layout.activity_showpicture)
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = uiOptions
        com.jqk.commonlibrary.util.StatusBarUtil.setPaddingSmart(this, b.contentView)
        // 小米刘海屏适配
        if (android.os.SystemProperties.getInt("ro.miui.notch", 0) == 1) {
            val flag = 0x00000100 or 0x00000200 or 0x00000400
            try {
                val method = Window::class.java!!.getMethod("addExtraFlags",
                        Int::class.javaPrimitiveType)
                method.invoke(window, flag)
            } catch (e: Exception) {
                Log.i("123", "addExtraFlags not found.")
            }
        }

        val dm = resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        density = dm.density

        imgPathList?.run {
            add("https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=6aeaafb44d34970a537e187df4a3baad/a8014c086e061d95c84250a377f40ad162d9ca21.jpg")
            add("https://file.izuiyou.com/img/view/id/500848543")
            add("https://file.izuiyou.com/img/view/id/503798117")
            add("https://file.izuiyou.com/img/view/id/501047609")
            add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545738381474&di=e0a11f9d203d03308fdeac66d74afa1d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F10%2F20150110202052_nwCkn.thumb.700_0.jpeg")
            add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546060912330&di=8c77fe191a0b845090f04b3a37be7ff2&imgtype=0&src=http%3A%2F%2Fbbsfiles.vivo.com.cn%2Fvivobbs%2Fattachment%2Fforum%2F201707%2F27%2F120851c3lugr4lbumfxz04.jpg")
        }

        for (i in 0..imgPathList.size - 1) {
            var viewType = ViewType(i, Constants.TYPE_NORMALPICTURE, imgPathList[i], null)
            typeList.add(viewType)
        }

        adaptRecyclerView()
    }

    // 模拟获取图片宽高和长图预览图
    fun processingPictures(imgPathList: MutableList<String>, observableEmitter: ObservableEmitter<ViewType>) {
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
                                resource.compress(Bitmap.CompressFormat.JPEG, 100, baos)
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

                                var viewType = ViewType(i, Constants.TYPE_LONGPICTURE, imgPathList[i], bt)
                                observableEmitter.onNext(viewType)
                            } else {
                                var viewType = ViewType(i, Constants.TYPE_NORMALPICTURE, imgPathList[i], resource)
                                observableEmitter.onNext(viewType)
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            Log.d("123", "onLoadFailed")
                            observableEmitter.onNext(ViewType(i, Constants.TYPE_ERROR, imgPathList[i], BitmapFactory.decodeResource(resources, R.drawable.icon_empty)))
                            super.onLoadFailed(errorDrawable)
                        }
                    })
        }
    }

    fun adaptRecyclerView() {
        // 适配recyclerview
        b.recyclerView.layoutManager = LinearLayoutManager(this@ShowPictureActivity)
        recyclerViewAdatper = RecyclerViewAdatper(this@ShowPictureActivity, typeList, screenWidth, screenHeight, density)
        recyclerViewAdatper.onItemClickListener = this@ShowPictureActivity
        b.recyclerView.adapter = recyclerViewAdatper
    }

    fun adaptViewPagerAndinitPictrueView(typeList: List<ViewType>) {
        for (i in 0..imgPathList.size - 1) {
            val pageView = PageView(this@ShowPictureActivity)
            pageView.loadPlacehoderResourse(typeList[i])
            viewList.add(pageView)
        }

        viewPagerAdatper = ViewPagerAdatper(viewList)
        vpb = DataBindingUtil.inflate(LayoutInflater.from(this@ShowPictureActivity), R.layout.layout_viewpager, null, false)
        vpb.viewPager.adapter = viewPagerAdatper
        vpb.viewPager.offscreenPageLimit = viewList.size
        vpb.viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                pictureView.onPageSelected(viewList.get(p0), typeList.get(p0))
            }
        })
        // 初始化pictureView
        bgView = LayoutInflater.from(this@ShowPictureActivity).inflate(R.layout.layout_bg, null)
        pictureView.setActivity(this@ShowPictureActivity)
        pictureView.setBgView(bgView)
        pictureView.setContentView(vpb.root)
        pictureView.setOnHideImgListener(this)
        b.frameLayout.addView(pictureView)
    }

    override fun onItemClick(position: Int, x: Int, y: Int, width: Int, height: Int) {
//        Log.d("123", "position = " + position)
//        Log.d("123", "x = " + x)
//        Log.d("123", "y = " + y)
//        Log.d("123", "width = " + width)
//        Log.d("123", "height = " + height)

        if (!firstClick) {
            firstClick = true
            adaptViewPagerAndinitPictrueView(typeList)
        }

        pictureView.setPageView(viewList.get(position), typeList.get(position))
        pictureView.show(x.toFloat(), 0f, y.toFloat(), 0f, width.toFloat(), height.toFloat())
        vpb.viewPager.setCurrentItem(position, false)
    }

    override fun hideImg() {
        for (pageView: PageView in viewList) {
            pageView.hideLongImg(true)
            pageView.hideNormalImg(true)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (pictureView != null && pictureView.isShown()) {
                pictureView.hide()
                return true
            }
            return super.onKeyDown(keyCode, event)
        }
        return super.onKeyDown(keyCode, event)
    }
}