package com.jqk.mydemo.dagger2;

import android.util.Log;

class ElectricHeater implements Heater {
  boolean heating;

  @Override public void on() {
    Log.d("dagger2", "~ ~ ~ heating ~ ~ ~");
    this.heating = true;
  }

  @Override public void off() {
    this.heating = false;
  }

  @Override public boolean isHot() {
    return heating;
  }
}
