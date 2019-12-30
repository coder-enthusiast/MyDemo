package com.jqk.mydemo.mvvmdagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.github.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(newsModel: NewsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}