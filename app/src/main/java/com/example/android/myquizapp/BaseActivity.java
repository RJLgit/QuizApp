package com.example.android.myquizapp;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ConnectivityReceiver mConnectivityReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        mConnectivityReceiver = new ConnectivityReceiver(this, this);
        if (!ConnectionUtils.haveNetworkConnection(this)) {
            ConnectionUtils.showDialog(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectivityReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mConnectivityReceiver);
    }
}
