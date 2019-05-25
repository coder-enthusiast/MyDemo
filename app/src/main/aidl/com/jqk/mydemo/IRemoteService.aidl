// IRemoteService.aidl
package com.jqk.mydemo;
import com.jqk.mydemo.IRemoteSerciceCallback;
// Declare any non-default types here with import statements

interface IRemoteService {
    /** Request the process ID of this service, to do evil things with it. */
    int getPid();

    void registerCallback(IRemoteSerciceCallback callback);

    void unregisterCallback(IRemoteSerciceCallback callback);
}
