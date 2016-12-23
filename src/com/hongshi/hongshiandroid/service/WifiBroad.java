package com.hongshi.hongshiandroid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/16.
 */
public class WifiBroad extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
        } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            Log.e("qf","网络状态改变");
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {// 如果断开连接
                Log.e("qf","wifi网络连接断开 ");
            }

            if (info.getState().equals(NetworkInfo.State.CONNECTING)) {
                Log.e("qf","连接到wifi网络");
            }
        } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            // WIFI开关
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {// 如果关闭

                Log.e("qf","系统关闭wifi");
            }
            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Log.e("qf","系统开启wifi");
            }
        }
    }
}
