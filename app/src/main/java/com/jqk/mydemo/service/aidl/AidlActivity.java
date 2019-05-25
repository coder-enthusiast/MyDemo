package com.jqk.mydemo.service.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jqk.mydemo.IRemoteSerciceCallback;
import com.jqk.mydemo.IRemoteService;
import com.jqk.mydemo.R;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class AidlActivity extends AppCompatActivity {

    private IRemoteService mIRemoteService;
    private Button aidlButton;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        aidlButton = (Button) findViewById(R.id.button);

        intent = new Intent();
        intent.setClass(this, RemoteService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

    }

    public void onClick(View view) {
        try {
            Toast.makeText(this, mIRemoteService.getPid() + "", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mIRemoteService = IRemoteService.Stub.asInterface(service);

            try {
                mIRemoteService.registerCallback(mCallback);
                startService(intent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("TAG", "Service has unexpectedly disconnected");
            try {
                mIRemoteService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                mIRemoteService = null;
            }
        }
    };

    private IRemoteSerciceCallback mCallback = new IRemoteSerciceCallback.Stub() {
        @Override
        public void onSuccess(int code) throws RemoteException {
            Log.d("jiqingke", "onSuccess = " + code);
        }

        @Override
        public void onFail() throws RemoteException {
            Log.d("jiqingke", "onError");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
