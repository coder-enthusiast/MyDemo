package com.jqk.mydemo.mvvmdagger

import dagger.Subcomponent

@Subcomponent
interface NewsComponent {
    @Subcomponent.Builder
    interface Factory {
        fun create(): NewsComponent
    }

    fun inflect(newsActivity: NewsActivity)
}