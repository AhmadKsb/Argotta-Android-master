package com.example.ahmad.chat_3.generalUtils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.ahmad.chat_3.R;

public class NotificationUtils {

    public static void sendNotification(Context context, String title, String text, Intent notificationIntent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context)
                .setContentTitle(title)
                .setContentText(text)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_message)
                .setAutoCancel(true)
                .setWhen(when)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, mNotifyBuilder.build());
    }
}
