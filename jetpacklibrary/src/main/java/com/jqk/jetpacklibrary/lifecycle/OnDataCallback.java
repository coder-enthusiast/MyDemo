package com.jqk.jetpacklibrary.lifecycle;

public interface OnDataCallback<T> {
    void onSuccess(T data);

    void onError();
}
