package com.jqk.mydemo.mvvm;

public class DoubleWeather {
    private Weather weather;
    private Weather weather1;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather1() {
        return weather1;
    }

    public void setWeather1(Weather weather1) {
        this.weather1 = weather1;
    }

    @Override
    public String toString() {
        return "DoubleWeather{" +
                "weather=" + weather +
                ", weather1=" + weather1 +
                '}';
    }
}
