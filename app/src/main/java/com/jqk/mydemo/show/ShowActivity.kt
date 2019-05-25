package com.jqk.mydemo.show

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.opengl.GLES10
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityShowBinding
import com.jqk.mydemo.glide.GlideApp
import com.jqk.mydemo.show.showpicture.ShowPictureActivity
import com.jqk.mydemo.show.showview.ShowViewActivity
import java.io.File
import javax.microedition.khronos.opengles.GL10

class ShowActivity : AppCompatActivity() {
    lateinit var b: ActivityShowBinding

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var density: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_show)
        b.view = this

        val dm = resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        density = dm.density

//        GlideApp.with(this)
//                .asBitmap()
//                .load("https://file.izuiyou.com/img/view/id/500848543")
//                .into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
//                        val width = resource?.getWidth()
//                        val height = resource?.getHeight()
//                        Log.d("123", "width = " + width)
//                        Log.d("123", "height = " + height)
//
//                        var n = ((screenWidth * 1.0 / width!! * 1.0) * height!!) / screenWidth
//                        Log.d("123", "n = " + n)
//                        if (n > 1.5) {
//
//                            var longWidth = (200 * (screenWidth * 1.0 / screenHeight * 1.0)).toInt()
//                            var longHeight = 200
//
//                            Log.d("123", "longWidth = " + longWidth)
//                            Log.d("123", "longHeight = " + longHeight)
//                            val lp = b.img.layoutParams
//                            lp.width = (longWidth * density).toInt()
//                            lp.height = (longHeight * density).toInt()
//                            b.img.layoutParams = lp
//
//                            // 截取长图的一部分，宽高跟规定的宽高相等
//                            var baos = ByteArrayOutputStream()
//                            resource.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//                            var inputStream = ByteArrayInputStream(baos.toByteArray())
//                            var decoder = BitmapRegionDecoder.newInstance(inputStream, true)
//
//                            val opts = BitmapFactory.Options()
//
//                            var rect = Rect()
//                            Log.d("123", "rectWidth = " + 200)
//                            Log.d("123", "rectHeight = " + 200 * (screenHeight * 1.0 / screenWidth * 1.0))
//                            rect.set(0, 0, 200, (200 * (screenHeight * 1.0 / screenWidth * 1.0)).toInt())
//                            var bm = decoder.decodeRegion(rect, opts)
//                            Log.d("123", "bmWidth = " + bm.width)
//                            Log.d("123", "bmHeight = " + bm.height)
//
//
//                        } else {
//
//                        }

//                        b.img.setMinimumScaleType(SCALE_TYPE_CUSTOM)
//                        b.img.minScale = 1.0f
//                        b.img.setImage(ImageSource.bitmap(resource!!))
//                    }
//
//                    override fun onStart() {
//                        super.onStart()
//                        Log.d("123", "onStart")
//                    }
//
//                    override fun onLoadFailed(errorDrawable: Drawable?) {
//                        super.onLoadFailed(errorDrawable)
//                        Log.d("123", "onLoadFailed")
//                    }
//                })

        GlideApp.with(this)
                .load("https://file.izuiyou.com/img/view/id/501047609")
                .downloadOnly(object : SimpleTarget<File>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        b.img.setDebug(true)
                        b.img.setImage(ImageSource.uri(Uri.fromFile(resource)))
                    }
                })

        val maxTextureSize = IntArray(1)
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0)
        val maxBitmapDimension = Math.max(maxTextureSize[0], 2048)
        Log.d("123", "maxBitmapDimension = " + maxBitmapDimension)
    }

    fun showView(view: View) {
        var intent = Intent()
        intent.setClass(this, ShowViewActivity::class.java)
        startActivity(intent)
    }

    fun showPicture(view: View) {
        var intent = Intent()
        intent.setClass(this, ShowPictureActivity::class.java)
        startActivity(intent)
    }
}