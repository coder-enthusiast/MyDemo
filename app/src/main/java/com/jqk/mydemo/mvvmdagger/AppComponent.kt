package com.jqk.mydemo.mvvmdagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, AndroidInjectionModule::class])
interface AppComponent {
    fun newsComponent(): NewsComponent.Factory

    fun Inject(app: App)
}