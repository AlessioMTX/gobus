package com.dfba.gobus;

/**
 * Created by claudio on 21/11/14.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.dfba.gobus.MyAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationService.class);
        context.startService(i);
    }
}