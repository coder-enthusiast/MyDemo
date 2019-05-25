package com.jqk.mydemo.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiqingke
 * on 2019/1/18
 */
@Singleton
@Component(modules = {DripCoffeeModule.class})
public interface CoffeeShop {
    CoffeeMaker maker();
}
