package com.jqk.mydemo.show.showpicture

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemPictrueBinding
import com.jqk.mydemo.glide.GlideApp
import com.jqk.mydemo.util.Constants
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class RecyclerViewAdatper : RecyclerView.Adapter<RecyclerViewAdatper.ViewHolder> {
    var datas: List<ViewType>
    var context: Context

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var density: Float = 0f

    constructor(context: Context, datas: List<ViewType>, screenWidth: Int, screenHeight: Int, density: Float) : super() {
        this.context = context
        this.datas = datas
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        this.density = density
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPictrueBinding>(LayoutInflater.from(p0.context), R.layout.item_pictrue, p0, false);
        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val b = DataBindingUtil.getBinding<ItemPictrueBinding>(p0.itemView)
        b?.run {
            type.visibility = View.GONE

            GlideApp
                    .with(context)
                    .asBitmap()
                    .load(datas[p1].path)
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

                                datas[p1].bitmap = bt
                                datas[p1].type = Constants.TYPE_LONGPICTURE
                                setImageTypeVisibility(Constants.TYPE_LONGPICTURE, type)
                                img.setImageBitmap(bt)
                            } else {
                                datas[p1].bitmap = resource
                                datas[p1].type = Constants.TYPE_NORMALPICTURE
                                setImageTypeVisibility(Constants.TYPE_NORMALPICTURE, type)
                                img.setImageBitmap(resource)
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            Log.d("123", "onLoadFailed")
                            datas[p1].bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon_empty)
                            datas[p1].type = Constants.TYPE_NORMALPICTURE
                            setImageTypeVisibility(Constants.TYPE_NORMALPICTURE, type)
                            img.setImageBitmap(datas[p1].bitmap)
                            super.onLoadFailed(errorDrawable)
                        }
                    })

            when (datas[p1].type) {
                Constants.TYPE_NORMALPICTURE -> {
                    type.visibility = View.GONE
                }
                Constants.TYPE_LONGPICTURE -> {
                    type.visibility = View.VISIBLE
                }
                Constants.TYPE_VIDEO -> {

                }
            }

            img.setOnClickListener({
                val location = IntArray(2)
                img.getLocationOnScreen(location)
//                Log.d("123", "img.measuredWidth = " + img.measuredWidth)
//                Log.d("123", "img.measuredHeight = " + img.measuredHeight)

                when (datas[p1].type) {
                    Constants.TYPE_NORMALPICTURE -> {
                        onItemClickListener?.onItemClick(p1, location[0], location[1], img.measuredWidth, img.measuredHeight)
                    }
                    Constants.TYPE_LONGPICTURE -> {
                        onItemClickListener?.onItemClick(p1, location[0], location[1], img.measuredWidth, img.measuredHeight)
                    }
                    Constants.TYPE_VIDEO -> {

                    }
                    Constants.TYPE_ERROR -> {
                        onItemClickListener?.onItemClick(p1, location[0], location[1], img.measuredWidth, img.measuredHeight)
                    }
                }
            })
        }
    }

    fun setImageTypeVisibility(type: Int, view: View) {
        when (type) {
            Constants.TYPE_NORMALPICTURE -> {
                view.visibility = View.GONE
            }
            Constants.TYPE_LONGPICTURE -> {
                view.visibility = View.VISIBLE
            }
            Constants.TYPE_VIDEO -> {

            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * x 控件当前横坐标
     * y 控件当前纵坐标
     * width 控件当前宽度
     * height 控件当前高度
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int, x: Int, y: Int, width: Int, height: Int)
    }
}