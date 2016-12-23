package com.hongshi.hongshiandroid.polling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hongshi.hongshiandroid.MainActivity;
import com.hongshi.hongshiandroid.R;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/23.
 */
public class PollingService extends Service {

    public static final String ACTION = "com.lambertlei.Services";
    private Notification mNotification;//消息通知器
    private NotificationManager mManager;//消息通知管理器
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("result", "轮询服务被创建onCreate");
//        initNotifiManager();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.i("result", "onStart");
        new PollingThread().start();
    }
    //初始化消息管理器
//    private void initNotifiManager() {
//        // TODO Auto-generated method stub
//        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        int icon = R.drawable.ic_launcher;
//        mNotification = new Notification();
//        mNotification.icon = icon;
//        mNotification.tickerText = "New Message";
//        mNotification.defaults |= Notification.DEFAULT_SOUND;
//        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//    }
    //弹出Notification
//    private void showNotification() {
//        mNotification.when = System.currentTimeMillis();
//        //Navigator to the new activity when click the notification title
//        Intent i = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
//                0);
//        mNotification.setLatestEventInfo(this,
//                getResources().getString(R.string.app_name), "You have new message!", pendingIntent);
//        mManager.notify(0, mNotification);
//    }
    int count =0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
            if (count % 5 == 0) {
                EventBus.getDefault().post("");

//                showNotification();
                System.out.println("New message!");
            }
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("result", "轮询服务销毁了");
    }
}
