package com.jqk.mydemo.mvp;

import com.jqk.mydemo.retrofit.RetrofitHttpRequest;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private CompositeDisposable compositeDisposable;
    private Observable<Login> observable;

    public LoginInteractorImpl() {
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
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

    @Override
    public void onDestroy() {
        if (observable != null) {
            compositeDisposable.clear();
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
