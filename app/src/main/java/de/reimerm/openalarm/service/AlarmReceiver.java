package de.reimerm.openalarm.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import de.reimerm.openalarm.alarm.Alarm;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SchedulingService} to do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: " + intent.toString());

        final Bundle bundle = intent.getExtras();
        final Intent serviceIntent = new Intent(context, SchedulingService.class);

        serviceIntent.putExtra(Alarm.TAG, bundle.getByteArray(Alarm.TAG));
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, serviceIntent);
    }
}
