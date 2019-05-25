package com.jqk.mydemo.recyclerview

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityRecyclerviewandedittextBinding

/**
 * Created by jiqingke
 * on 2019/1/26
 */
class RecyclerViewAndEditTextActivity : AppCompatActivity(), MyAdapter.OnClickListener {
    lateinit var binding: ActivityRecyclerviewandedittextBinding
    var modify = false
    val datas: MutableList<Data> by lazy {
        mutableListOf<Data>()
    }

    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerviewandedittext)
        binding.view = this

        datas.add(Data(""))
        myAdapter = MyAdapter(this, datas, binding.parentView)
        myAdapter.onClickListener = this
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter.isModify = false
    }

    override fun add() {
        val data = Data("")
        myAdapter.addData(data)
    }

    override fun delete(position: Int) {
        myAdapter.removeData(position)
    }

    fun modify(view: View) {
        if (modify) {
            Log.d("jiqingke", "提交的数据 = " + datas.toString())
        } else {
            myAdapter.isModify = true
            modify = true
        }
    }
}