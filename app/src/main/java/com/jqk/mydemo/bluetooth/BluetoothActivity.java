package com.jqk.mydemo.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jqk.mydemo.R;

import java.util.UUID;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final UUID MY_UUID =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private Button start, stop;
    private RecyclerView recyclerView;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice device;
    private Boolean isBond = false;
    private BluetoothService bluetoothService;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
//                    Log.d("123", "扫描开始");
//                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                    Log.d("123", "device.getName() = " + device.getName());
//                    Log.d("123", "device.getAddress() = " + device.getAddress());
//
//                    if (device.getAddress().equals("20:18:04:10:06:56")) {
//                        mBluetoothAdapter.cancelDiscovery();
//                        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
//                        Log.d("123", "已配对的设备 = " + devices.toString());
//                        for (BluetoothDevice bluetoothDevice : devices) {
//                            if (bluetoothDevice.getAddress().equals("20:18:04:10:06:56")) {
//                                isBond = true;
//                            }
//                        }
//                        if (!isBond) {
//                            device.createBond();
//                        } else {
////                            new ConnectThread(device).start();
//
//                            if (bluetoothService == null) {
//                                bluetoothService = new BluetoothService(BluetoothActivity.this, null);
//                            }
//                            if (bluetoothService != null) {
//                                bluetoothService.connect(device, 1);
//                            }
//                        }
//                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.d("123", "扫描结束");
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                    switch (state) {
                        case BluetoothDevice.BOND_NONE:
                            Log.d("123", "删除配对");
                            break;
                        case BluetoothDevice.BOND_BONDING:
                            Log.d("123", "正在配对");
                            break;
                        case BluetoothDevice.BOND_BONDED:
                            Log.d("123", "配对成功");
                            break;
                    }
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Log.d("123", "连接成功");
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Log.d("123", "连接断开");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        init();

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        requestPower();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

//        BluetoothDevice.BOND_BONDING//正在配对
//        BluetoothDevice.BOND_NONE//配对取消
//        BluetoothDevice.BOND_BONDED//配对成功
//        BluetoothDevice.ACTION_FOUND// 发现到蓝牙设备
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }

    public void init() {
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                Log.d("123", "已拒绝");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                Log.d("123", "申请");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }
        } else {
            Log.d("123", "已有权限");
        }
    }

    public void scanBT() {
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                scanBT();
                break;
            case R.id.stop:
                if (bluetoothService != null) {
                    bluetoothService.stop();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // 确定
                Toast.makeText(this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
            }

            if (resultCode == RESULT_CANCELED) {
                // 取消
                Toast.makeText(this, "拒绝开启蓝牙", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
