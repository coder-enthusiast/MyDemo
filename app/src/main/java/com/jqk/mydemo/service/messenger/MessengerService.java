package com.jqk.mydemo.service.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

//以下是 Messenger 的使用方法摘要：
//
//        服务实现一个 Handler，由其接收来自客户端的每个调用的回调
//        Handler 用于创建 Messenger 对象（对 Handler 的引用）
//        Messenger 创建一个 IBinder，服务通过 onBind() 使其返回客户端
//        客户端使用 IBinder 将 Messenger（引用服务的 Handler）实例化，然后使用后者将 Message 对象发送给服务
//        服务在其 Handler 中（具体地讲，是在 handleMessage() 方法中）接收每个 Message。
//        这样，客户端并没有调用服务的“方法”。而客户端传递的“消息”（Message 对象）是服务在其 Handler 中接收的。

public class MessengerService extends Service {
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
}
