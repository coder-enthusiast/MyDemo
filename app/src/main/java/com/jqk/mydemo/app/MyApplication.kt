package com.jqk.mydemo.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.jqk.mydemo.koin.HelloRepository
import com.jqk.mydemo.koin.HelloRepositoryImpl
import com.jqk.mydemo.koin.MyViewModel

class MyApplication : Application() {
//    val appModule = module {
//
//        // single instance of HelloRepository
//        single<HelloRepository> { HelloRepositoryImpl() }
//
//        // MyViewModel ViewModel
//        viewModel { MyViewModel(get()) }
//    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

//        startKoin{
//            androidLogger()
//            androidContext(this@MyApplication)
//            modules(appModule)
//        }
    }
}