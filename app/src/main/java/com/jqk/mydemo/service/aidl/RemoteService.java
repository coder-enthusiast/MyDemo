package com.jqk.mydemo.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.jqk.mydemo.IRemoteSerciceCallback;
import com.jqk.mydemo.IRemoteService;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

//调用类必须执行以下步骤，才能调用使用 AIDL 定义的远程接口：
//
//        在项目 src/ 目录中加入 .aidl 文件。
//        声明一个 IBinder 接口实例（基于 AIDL 生成）。
//        实现 ServiceConnection。
//        调用 Context.bindService()，以传入您的 ServiceConnection 实现。
//        在您的 onServiceConnected() 实现中，您将收到一个 IBinder 实例（名为 service）。调用 YourInterfaceName.Stub.asInterface((IBinder)service)，以将返回的参数转换为 YourInterface 类型。
//        调用您在接口上定义的方法。您应该始终捕获 DeadObjectException 异常，它们是在连接中断时引发的；这将是远程方法引发的唯一异常。
//        如需断开连接，请使用您的接口实例调用 Context.unbindService()。
//        有关调用 IPC 服务的几点说明：
//
//        对象是跨进程计数的引用。
//        您可以将匿名对象作为方法参数发送。

public class RemoteService extends Service {

    final RemoteCallbackList<IRemoteSerciceCallback> mCallbacks = new RemoteCallbackList<IRemoteSerciceCallback>();

    @Override
    public void onCreate() {
        Log.d("jiqingke", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jiqingke", "onStartCommand");
        callback();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        Log.d("jiqingke", "onBind");
        return mBinder;
    }


    void callback() {
        final int N = mCallbacks.beginBroadcast();
        Log.d("jiqingke", "N = " + N);
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).onSuccess(i);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }


    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return 2;
        }

        @Override
        public void registerCallback(IRemoteSerciceCallback callback) throws RemoteException {
            Log.d("jiqingke", "registerCallback");
            if (callback != null) {
                mCallbacks.register(callback);
            }
        }

        @Override
        public void unregisterCallback(IRemoteSerciceCallback callback) throws RemoteException {
            Log.d("jiqingke", "unregisterCallback");
            if (callback != null) {
                mCallbacks.unregister(callback);
            }
        }
    };
}
