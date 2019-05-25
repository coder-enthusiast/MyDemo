package com.jqk.mydemo.mvvm;

public interface OnDataCallback<T> {
    void onSuccess(T data);

    void onError();
}
