package com.preethzcodez.ecommerce.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.preethzcodez.ecommerce.database.DB_Handler;
import com.preethzcodez.ecommerce.database.SessionManager;
import androidx.annotation.Nullable;

public class SyncDBPrices extends IntentService {

    DB_Handler db_handler;
    SessionManager sessionManager;
    Intent intent;
    final String LOG_TAG = "SyncDBLogs";
    String client_code;

    public SyncDBPrices(String name) {
        super(name);
    }
    public SyncDBPrices() {
        super("SyncDBPrices");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        db_handler = new DB_Handler(this);
        this.intent = intent;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroi");
    }



}
