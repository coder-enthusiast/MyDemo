package com.jqk.mydemo.mvp;

import android.util.Log;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LoginPresenterImpl implements LoginPresenter, OnDataCallback<Login>{

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void login() {
        String userName = loginView.getUserName();
        String passWord = loginView.getPassword();
        loginView.showProgress();
        loginInteractor.login(userName, passWord, this);
    }

    @Override
    public void onSuccess(Login login) {
        Log.d("123", login.toString());
        loginView.hideProgress();
    }

    @Override
    public void onError() {
        loginView.hideProgress();
    }

    @Override
    public void onDestroy() {
        loginInteractor.onDestroy();
    }
}
