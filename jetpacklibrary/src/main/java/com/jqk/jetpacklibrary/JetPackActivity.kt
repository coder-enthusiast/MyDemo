package com.jqk.jetpacklibrary

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jqk.jetpacklibrary.databinding.ActivityJetpackBinding
import com.jqk.jetpacklibrary.databinding.DBTestActivity
import com.jqk.jetpacklibrary.navigation.NavigationActivity
import com.jqk.jetpacklibrary.navigation.NavigationUIActivity
import com.jqk.jetpacklibrary.paging.PagingActivity
import com.jqk.jetpacklibrary.room.RoomActivity
import com.jqk.jetpacklibrary.viewmodel.ViewModelActivity
import com.jqk.jetpacklibrary.workmanager.WorkManagerActivity

class JetPackActivity : AppCompatActivity() {
    lateinit var binding: ActivityJetpackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack)
        binding.view = this
    }

    fun databinding(view: View) {
        jumpActivity(DBTestActivity().javaClass)
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

    fun viewModel(view: View) {
        jumpActivity(ViewModelActivity().javaClass)
    }

    fun jumpActivity(clazz: Class<Any>) {
        var intent = Intent()
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}