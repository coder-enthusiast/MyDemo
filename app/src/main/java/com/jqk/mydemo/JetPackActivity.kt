package com.jqk.mydemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.databinding.ActivityJetpackBinding
import com.jqk.mydemo.jetpack.navigation.NavigationActivity
import com.jqk.mydemo.jetpack.navigation.NavigationUIActivity
import com.jqk.mydemo.jetpack.paging.PagingActivity
import com.jqk.mydemo.jetpack.room.RoomActivity
import com.jqk.mydemo.jetpack.workmanager.WorkManagerActivity

class JetPackActivity : AppCompatActivity() {
    lateinit var binding: ActivityJetpackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack)
        binding.view = this
    }

    fun navigation(view: View) {
        jumpActivity(NavigationActivity().javaClass)
    }

    fun navigationUI(view: View) {
        jumpActivity(NavigationUIActivity().javaClass)
    }

    fun paging(view: View) {
        jumpActivity(PagingActivity().javaClass)
    }

    fun room(view: View) {
        jumpActivity(RoomActivity().javaClass)
    }

    fun workManager(view: View) {
        jumpActivity(WorkManagerActivity().javaClass)
    }

    fun jumpActivity(clazz: Class<Any>) {
        var intent = Intent()
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}