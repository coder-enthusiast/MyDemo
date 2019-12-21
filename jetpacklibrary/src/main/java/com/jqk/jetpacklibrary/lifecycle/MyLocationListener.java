package com.jqk.jetpacklibrary.lifecycle;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import static androidx.lifecycle.Lifecycle.State.STARTED;


public class MyLocationListener implements LifecycleObserver {
    private boolean enabled = false;

    private Lifecycle lifecycle;

    public MyLocationListener(Context context, Lifecycle lifecycle, OnDataCallback onDataCallback) {
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (enabled) {
            // connect
        }
    }

    public void enable() {
        enabled = true;
        if (lifecycle.getCurrentState().isAtLeast(STARTED)) {
            // connect if not connected
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        // disconnect if connected
    }
}
