package com.jqk.mydemo.mvp;

/**
 * Created by Administrator on 2018/7/1.
 */

public interface LoginInteractor {

    void login(String userName, String passWord, OnDataCallback<Login> getDataCallback);

    void onDestroy();
}
