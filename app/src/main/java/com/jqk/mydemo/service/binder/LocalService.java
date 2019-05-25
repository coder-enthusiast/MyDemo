package com.jqk.mydemo.service.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

//    1.在您的服务中，创建一个可满足下列任一要求的 Binder 实例：
//            (1)包含客户端可调用的公共方法
//            (2)返回当前 Service 实例，其中包含客户端可调用的公共方法
//            (3)或返回由服务承载的其他类的实例，其中包含客户端可调用的公共方法
//    2.从 onBind() 回调方法返回此 Binder 实例。
//    3.在客户端中，从 onServiceConnected() 回调方法接收 Binder，并使用提供的方法调用绑定服务。

public class LocalService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * method for clients
     */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
