package com.jqk.mydemo.recyclerview

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ItemRecyclerviewBinding

/**
 * Created by jiqingke
 * on 2019/1/26
 */
class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder> {

    var context: Context
    lateinit var datas: MutableList<Data>
    var parentView: View
    var inputMethodManager: InputMethodManager
    var checkPos: Int = -1

    var isModify: Boolean = false
        set(value) {
            field = value
            Log.d("jiqingke", "value = " + value)
            notifyDataSetChanged()
        }

    val strList: MutableList<String> by lazy {
        mutableListOf<String>()
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            strList.set(checkPos, s.toString())
            datas[checkPos].str = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

//    constructor(context: Context, datas: MutableList<Data>) : super() {
//        this.context = context
//        this.datas = datas
//
//        inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
//
//        for (data: Data in datas) {
//            strList.add(data.str)
//        }
//    }

    constructor(context: Context, datas: MutableList<Data>, parentView: View) : super() {
        this.context = context
        this.datas = datas
        this.parentView = parentView

        inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;

        for (data: Data in datas) {
            strList.add(data.str)
        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRecyclerviewBinding>(
                LayoutInflater.from(context),
                R.layout.item_recyclerview,
                p0,
                false
        )

        val viewHolder = ViewHolder(binding.root)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        DataBindingUtil.getBinding<ItemRecyclerviewBinding>(p0.itemView)?.run {
            Log.d("jiqingke", "isModify = " + isModify)
            if (isModify) {
                editText.isFocusableInTouchMode = true
                editText.isFocusable = true

                add.isEnabled = true
                delete.isEnabled = true
            } else {
                editText.isFocusableInTouchMode = false
                editText.isFocusable = false

                parentView.isFocusableInTouchMode = true
                parentView.isFocusable = true
                add.isEnabled = false
                delete.isEnabled = false
            }

            editText.setText(strList[p1])
            add.setOnClickListener {
                onClickListener?.add()
            }

            delete.setOnClickListener {
                onClickListener?.delete(p1)
            }

            editText.setOnFocusChangeListener { view, b ->
                if (b) {
                    checkPos = p1
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
//        Log.e("jiqingke", "隐藏item=" + holder.getAdapterPosition());
        DataBindingUtil.getBinding<ItemRecyclerviewBinding>(holder.itemView)?.run {
            editText.removeTextChangedListener(textWatcher)
            editText.clearFocus()
            if (checkPos == holder.adapterPosition) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        DataBindingUtil.getBinding<ItemRecyclerviewBinding>(holder.itemView)?.run {
            editText.addTextChangedListener(textWatcher)
            if (checkPos == holder.adapterPosition) {
                editText.setSelection(editText.getText().length)
            }
        }
    }

    fun addData(data: Data) {
        datas.add(data)
        strList.add("")
        isModify = true
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        datas.removeAt(position)
        strList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    interface OnClickListener {
        fun add()
        fun delete(position: Int)
    }

    lateinit var onClickListener: OnClickListener

}