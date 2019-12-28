package com.jqk.mydemo.dagger2.coffee;

import android.util.Log;

import javax.inject.Inject;

class Thermosiphon implements Pump {
  private final Heater heater;

  @Inject
  Thermosiphon(Heater heater) {
    this.heater = heater;
  }

  @Override public void pump() {
    if (heater.isHot()) {
      Log.d("dagger2", "=> => pumping => =>");
    }
  }
}
