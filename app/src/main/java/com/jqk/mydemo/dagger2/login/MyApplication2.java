package com.jqk.mydemo.dagger2.login;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication2 extends Application {
    // Reference to the application graph that is used across the whole app
    ApplicationComponent appComponent = DaggerApplicationComponent.create();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
