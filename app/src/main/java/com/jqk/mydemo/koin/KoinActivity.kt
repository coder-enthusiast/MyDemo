package com.jqk.mydemo.koin

import org.koin.dsl.module

class KoinActivity {
    val girlModule = module {
        factory {

            Girl()
        }
    }
}