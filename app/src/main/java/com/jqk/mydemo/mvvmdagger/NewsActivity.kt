package com.jqk.mydemo.mvvmdagger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityNews2Binding
import javax.inject.Inject

class NewsActivity : AppCompatActivity(){
    lateinit var binding: ActivityNews2Binding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var newComponent: NewsComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news2)

        binding.view = this

        newComponent = (applicationContext as App).appComponent.newsComponent().create()
        newComponent.inflect(this)
    }

    fun load(view: View) {
        viewModel.getData()
    }
}