package com.jqk.mydemo.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;

import com.jqk.mydemo.IRectInterface;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class RectService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mBinder;
    }

    private final IRectInterface.Stub mBinder = new IRectInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String inRectInfo(Rect rect) throws RemoteException {
            return "Rect{" +
                    "left=" + rect.left +
                    ", top=" + rect.top +
                    ", right=" + rect.right +
                    ", bottom=" + rect.bottom +
                    '}';
        }

        @Override
        public String outRectInfo(Rect rect) throws RemoteException {
            return "Rect{" +
                    "left=" + rect.left +
                    ", top=" + rect.top +
                    ", right=" + rect.right +
                    ", bottom=" + rect.bottom +
                    '}';
        }

        @Override
        public String inOutRectInfo(Rect rect) throws RemoteException {
            return "Rect{" +
                    "left=" + rect.left +
                    ", top=" + rect.top +
                    ", right=" + rect.right +
                    ", bottom=" + rect.bottom +
                    '}';
        }

    };
}
