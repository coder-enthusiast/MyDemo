package com.jqk.mydemo.mvvm;

import com.jqk.mydemo.mvp.Login;
import com.jqk.mydemo.retrofit.RetrofitHttpRequest;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {
    private CompositeDisposable compositeDisposable;
    private Observable<Login> observable;
    private Observable<DoubleWeather> observable1;

    public LoginModel() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public void login(String userName, String passWord, final OnDataCallback<Login> onDataCallback) {
        observable = RetrofitHttpRequest.getInstance().retrofitService.login(userName, passWord);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Login login) {
                        onDataCallback.onSuccess(login);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataCallback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getDataDouble(final OnDataCallback<DoubleWeather> onDataCallback) {
        observable1 = Observable.zip(RetrofitHttpRequest.getInstance().retrofitService.getWeather(), RetrofitHttpRequest.getInstance().retrofitService.getWeather1(), new BiFunction<Weather, Weather, DoubleWeather>() {
            @Override
            public DoubleWeather apply(Weather weather, Weather weather2) throws Exception {
                DoubleWeather doubleWeather = new DoubleWeather();
                doubleWeather.setWeather(weather);
                doubleWeather.setWeather1(weather2);
                return doubleWeather;
            }
        });
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DoubleWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DoubleWeather doubleWeather) {
                        onDataCallback.onSuccess(doubleWeather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataCallback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void onDestroy() {
        if (observable != null) {
            compositeDisposable.clear();
            observable.unsubscribeOn(Schedulers.io());
        }

        if (observable1 != null) {
            compositeDisposable.clear();
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
