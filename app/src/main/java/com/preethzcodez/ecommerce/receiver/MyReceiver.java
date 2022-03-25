package com.preethzcodez.ecommerce.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.preethzcodez.ecommerce.BuildConfig;

public class MyReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "BadgesReceiver";

    public static final String SIMPLE_ACTION = BuildConfig.APPLICATION_ID + ".SIMPLE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive");
        Toast.makeText(context, intent.getAction(),Toast.LENGTH_SHORT).show();

    }
}