package com.jqk.mydemo.mvvm;

import android.util.Log;
import android.view.View;

import com.jqk.mydemo.databinding.ActivityLoginbdBinding;
import com.jqk.mydemo.mvp.Login;

public class LoginViewModel{
    private LoginActivity view;
    private ActivityLoginbdBinding binding;
    private LoginModel model;

    public LoginViewModel(LoginActivity view, ActivityLoginbdBinding binding) {
        this.view = view;
        this.binding = binding;
        binding.setViewModel(this);
        model = new LoginModel();
    }

    public void login(View v) {
        String userName = view.getUserName();
        String passWord = view.getPassWord();

        view.showProgress();
        model.login(userName, passWord, new OnDataCallback<Login>() {
            @Override
            public void onSuccess(Login data) {
                Log.d("123", "login = " + data.toString());
                view.hideProgress();
            }

            @Override
            public void onError() {
                view.hideProgress();
            }
        });
    }

    public void getData(View v) {
        view.showProgress();
        model.getDataDouble(new OnDataCallback<DoubleWeather>() {
            @Override
            public void onSuccess(DoubleWeather data) {
                view.hideProgress();
                Log.d("123", "Data = " + data.toString());
            }

            @Override
            public void onError() {
                view.hideProgress();
            }
        });
    }



    public void onDestroy() {
        model.onDestroy();
    }
}
