package com.jqk.mydemo.jetpack.lifecycle;

public interface OnDataCallback<T> {
    void onSuccess(T data);

    void onError();
}
