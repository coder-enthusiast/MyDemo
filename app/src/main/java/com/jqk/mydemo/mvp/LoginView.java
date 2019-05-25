package com.jqk.mydemo.mvp;

/**
 * Created by Administrator on 2018/7/1.
 */

public interface LoginView {
    String getUserName();

    String getPassword();

    void showProgress();

    void hideProgress();
}
