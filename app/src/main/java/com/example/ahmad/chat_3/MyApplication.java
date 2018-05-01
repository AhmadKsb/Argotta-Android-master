package com.example.ahmad.chat_3;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.ahmad.chat_3.activities.MainActivity;
import com.example.ahmad.chat_3.db.Db;

public class MyApplication extends Application {

    private boolean chatActvityVisibile;

    @Override
    public void onCreate() {
        super.onCreate();
        if (loggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    public boolean isChatActvityVisibile() {
        return chatActvityVisibile;
    }

    public void setChatActivityVisibile(boolean chatActvityVisibile) {
        this.chatActvityVisibile = chatActvityVisibile;
    }

    public boolean loggedIn() {
        System.out.println(1);
        return getSharedPreferences("prefs", Context.MODE_PRIVATE).getBoolean("loggedIn", false);
    }

    public Db getDb() {
        return Room.databaseBuilder(getApplicationContext(), Db.class, "db").build();
    }

    // required to send notifications for api > 26
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The id of the channel.
        String id = "my_channel_01";

        // The user-visible name of the channel.
        CharSequence name = "Notification Channel 01";

        // The user-visible description of the channel.
        String description = "insert description here";

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);

        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);

        mNotificationManager.createNotificationChannel(mChannel);
    }


}