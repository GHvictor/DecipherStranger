package com.android.decipherstranger.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Feng on 2015/04/11.
 */
public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        if (currentNetworkInfo.isConnected()) {
            System.out.println("connected again");

            if (!NetworkService.getInstance().getIsConnected()) {
                NetworkService.getInstance().setupConnection();
            }
        } else {
            NetworkService.getInstance().closeConnection();
        }
    }
}