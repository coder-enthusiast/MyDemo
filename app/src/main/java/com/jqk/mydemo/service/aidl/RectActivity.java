package com.jqk.mydemo.service.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jqk.mydemo.IRectInterface;
import com.jqk.mydemo.R;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class RectActivity extends AppCompatActivity {
    private IRectInterface mIRectInterface;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);

        button = (Button) findViewById(R.id.button);

        Intent intent = new Intent();
        intent.setClass(this, RectService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void onClick(View view) {
        try {
            String msg = mIRectInterface.inRectInfo(new Rect(1, 1, 1, 1)) +
                    mIRectInterface.outRectInfo(new Rect(2, 2, 2, 2)) +
                    mIRectInterface.inOutRectInfo(new Rect(3, 3, 3, 3));

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mIRectInterface = IRectInterface.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("TAG", "Service has unexpectedly disconnected");
            mIRectInterface = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
