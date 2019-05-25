package com.jqk.mydemo.mvp;

public interface OnDataCallback<T> {
    void onSuccess(T Data);

    void onError();
}
