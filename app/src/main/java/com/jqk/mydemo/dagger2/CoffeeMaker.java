package com.jqk.mydemo.dagger2;

import android.util.Log;

import dagger.Lazy;

import javax.inject.Inject;

class CoffeeMaker {
    private final Lazy<Heater> heater; // Create a possibly costly heater only when we use it.
    private final Pump pump;

    @Inject
    CoffeeMaker(Lazy<Heater> heater, Pump pump) {
        this.heater = heater;
        this.pump = pump;
    }

    public void brew() {
        heater.get().on();
        pump.pump();
        Log.d("dagger2", " [_]P coffee! [_]P ");
        heater.get().off();
    }
}
