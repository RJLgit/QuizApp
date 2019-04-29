package com.example.android.myquizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.android.myquizapp.NotificationUtils.UNIQUE_QUIZ_APP_NOTIFICATION_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");
       // if (remoteMessage.getData().size() > 0) {

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            Log.d(TAG, "onMessageReceived: " + title);
            Log.d(TAG, "onMessageReceived: " + message);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        NotificationUtils.NOTIFICATION_CHANNEL_ID_QUIZ_APP, getApplicationContext().getString(R.string.main_notification_channel_name),
                        NotificationManager.IMPORTANCE_HIGH);
                mChannel.setShowBadge(true);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), NotificationUtils.NOTIFICATION_CHANNEL_ID_QUIZ_APP)
                            .setSmallIcon(R.drawable.tiny_notifcation_image)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message)
                                    .setBigContentTitle(title)
                                    .setSummaryText("New Questions"))

                            .setLargeIcon(NotificationUtils.getNotIcon(getApplicationContext()))

                            .setContentIntent(NotificationUtils.contentIntent(getApplicationContext()))
                            .setAutoCancel(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }
            notificationManager.notify(UNIQUE_QUIZ_APP_NOTIFICATION_ID, notificationBuilder.build());

    }
}
