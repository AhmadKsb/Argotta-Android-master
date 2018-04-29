package com.example.ahmad.chat_3.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog spinner;
    private GlobalReceiver globalReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // portrait only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        spinner = new ProgressDialog(this);

        // register receivers
        globalReceiver = new GlobalReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(globalReceiver,
                new IntentFilter("global"));
    }

    public SharedPreferences getPrefs() {
        return getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void onRequestStart() {
        if (spinner != null) {
            System.out.println("SHOWING DIALOG");
            spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            spinner.setMessage("Please wait....");
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.show();
        }
    }

    public void onRequestEnd() {
        if (spinner != null && spinner.isShowing())
            spinner.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (spinner != null) {
            if (spinner.isShowing())
                spinner.dismiss();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(globalReceiver);
        super.onDestroy();
    }


    private class GlobalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }

        private void sendNotification(Context context, String title, String text,
                                      int iconId, Intent notificationIntent) {
            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon(iconId)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSound(alarmSound)
                    .setAutoCancel(true)
                    .setWhen(when)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(1, mNotifyBuilder.build());
        }
    }
}