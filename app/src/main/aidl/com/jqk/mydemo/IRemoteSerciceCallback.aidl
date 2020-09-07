// IRemoteSerciceCallback.aidl
package com.jqk.mydemo;

// Declare any non-default types here with import statements

interface IRemoteSerciceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void onSuccess(int code);

    void onFail(in Rect rect);

}
