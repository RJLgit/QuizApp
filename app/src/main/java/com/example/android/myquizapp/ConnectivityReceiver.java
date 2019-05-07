package com.example.android.myquizapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = "ConnectivityReceiver";
    private Context context;
    private Activity activity;

    public ConnectivityReceiver(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive: ");
        if ( ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (!ConnectionUtils.haveNetworkConnection(context)) {
                ConnectionUtils.showDialog(activity);
            }
        }
    }
}
